package com.example.voebb.service.impl;

import com.example.voebb.exception.UserNotFoundException;
import com.example.voebb.model.dto.user.UserDTO;
import com.example.voebb.model.dto.user.UserRegistrationDTO;
import com.example.voebb.model.entity.CustomUser;
import com.example.voebb.model.entity.CustomUserRole;
import com.example.voebb.repository.CustomUserRepo;
import com.example.voebb.repository.CustomUserRoleRepo;
import com.example.voebb.service.CustomUserService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService, CustomUserService {

    private final CustomUserRepo userRepo;
    private final CustomUserRoleRepo customUserRoleRepo;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserRepo customUserRepo;

    public CustomUserDetailsService(CustomUserRepo userRepo, CustomUserRoleRepo customUserRoleRepo, PasswordEncoder passwordEncoder, CustomUserRepo customUserRepo) {
        this.userRepo = userRepo;
        this.customUserRoleRepo = customUserRoleRepo;
        this.passwordEncoder = passwordEncoder;
        this.customUserRepo = customUserRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomUser customUser = userRepo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        List<SimpleGrantedAuthority> authorities = customUser.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .toList();

        return new User(
                customUser.getEmail(),
                customUser.getPassword(),
                customUser.isEnabled(),
                true, true, true,
                authorities
        );
    }

    @Transactional
    public void registerUser(UserRegistrationDTO userRegistrationDTO) {
        CustomUserRole role = customUserRoleRepo.findByName("ROLE_CLIENT")
                .orElseThrow(() -> new RuntimeException("ROLE_CLIENT not found"));

        CustomUser customUser = new CustomUser();

        customUser.setEmail(userRegistrationDTO.email());
        customUser.setPassword(passwordEncoder.encode(userRegistrationDTO.password()));
        customUser.setFirstName(userRegistrationDTO.firstName());
        customUser.setLastName(userRegistrationDTO.lastName());
        customUser.setEnabled(true);
        customUser.setRoles(Set.of(role));

        customUserRepo.save(customUser);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long id) {
        CustomUser user = userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
        return toDto(user);
    }

    @Override
    @Transactional
    public UserDTO createUser(UserDTO userDto) {
        CustomUser user = fromDto(userDto);
        CustomUser saved = userRepo.save(user);
        return toDto(saved);
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDto) {
        CustomUser existingUser = userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));

        existingUser.setFirstName(userDto.firstName());
        existingUser.setLastName(userDto.lastName());
        existingUser.setEmail(userDto.email());
        existingUser.setEnabled(userDto.enabled());
        existingUser.setBorrowedBooksCount(userDto.borrowedBooksCount());

        if (userDto.password() != null && !userDto.password().isBlank()) {
            existingUser.setPassword(userDto.password());
        }

        CustomUser updatedUser = userRepo.save(existingUser);
        return toDto(updatedUser);
    }

    @Override
    public void enableUser(Long id) {
        CustomUser user = userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
        user.setEnabled(true);
        userRepo.save(user);
    }

    @Override
    public void disableUser(Long id) {
        CustomUser user = userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
        user.setEnabled(false);
        userRepo.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }

    // Mapping methods
    private UserDTO toDto(CustomUser user) {
        return new UserDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.isEnabled(),
                user.getBorrowedBooksCount(),
                null // Do not expose password
        );
    }

    private CustomUser fromDto(UserDTO dto) {
        CustomUser user = new CustomUser();
        user.setId(dto.id()); // Optional: depending on whether you're creating or updating
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        user.setEmail(dto.email());
        user.setEnabled(dto.enabled());
        user.setBorrowedBooksCount(dto.borrowedBooksCount());
        user.setPassword(dto.password());
        return user;
    }

}
