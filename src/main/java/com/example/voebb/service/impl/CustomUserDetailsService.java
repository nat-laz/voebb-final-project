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
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService, CustomUserService {

    private final CustomUserRepo userRepo;
    private final CustomUserRoleRepo customUserRoleRepo;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserRepo customUserRepo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        CustomUser customUser;

        if (isValidEmail(username)) {
            customUser = userRepo.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Email not found: " + username));
        } else if (isValidPhone(username)) {
            customUser = userRepo.findByPhoneNumber(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Phone number not found: " + username));
        } else {
            throw new UsernameNotFoundException("Invalid login identifier: " + username);
        }

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

    private boolean isValidPhone(String username) {
        return username.matches("^\\+|0?[0-9]{10,15}$");
    }

    private boolean isValidEmail(String username) {
        return username.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }

    @Transactional
    public void registerUser(UserRegistrationDTO userRegistrationDTO) {
        CustomUserRole role = customUserRoleRepo.findByName("ROLE_CLIENT")
                .orElseThrow(() -> new RuntimeException("ROLE_CLIENT not found"));

        CustomUser customUser = new CustomUser();

        customUser.setEmail(userRegistrationDTO.email());
        customUser.setPhoneNumber(userRegistrationDTO.phoneNumber());
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
        CustomUser user = toEntity(userDto);
        CustomUser saved = userRepo.save(user);
        return toDto(saved);
    }

    @Override
    @Transactional
    public UserDTO updateUser(Long id, UserDTO userDto) {
        CustomUser existingUser = userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));

        Set<CustomUserRole> userRoles = userDto.roleIds().stream()
                .map(roleId -> customUserRoleRepo.findById(roleId)
                        .orElseThrow(() -> new RuntimeException("Role not found")))
                .collect(Collectors.toSet());

        existingUser.setFirstName(userDto.firstName());
        existingUser.setLastName(userDto.lastName());
        existingUser.setEmail(userDto.email());
        existingUser.setPhoneNumber(userDto.phoneNumber());
        existingUser.setEnabled(userDto.enabled());
        existingUser.setBorrowedBooksCount(userDto.borrowedBooksCount());

        if (userDto.password() != null && !userDto.password().isBlank()) {
            existingUser.setPassword(userDto.password());
        }

        existingUser.setRoles(userRoles);

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
        List<Long> roleIds = user.getRoles().stream()
                .map(CustomUserRole::getId)
                .toList();

        return new UserDTO(
                user.getId(),
                user.getEmail(),
                user.getPhoneNumber(),
                null, // Do not expose password
                user.getFirstName(),
                user.getLastName(),
                user.isEnabled(),
                user.getBorrowedBooksCount(),
                roleIds
        );
    }

    private CustomUser toEntity(UserDTO dto) {
        Set<CustomUserRole> userRoles = dto.roleIds().stream()
                .map(roleId -> customUserRoleRepo.findById(roleId)
                        .orElseThrow(() -> new RuntimeException("Role not found")))
                .collect(Collectors.toSet());

        CustomUser user = new CustomUser();
        user.setId(dto.id()); // Optional: depending on whether you're creating or updating
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        user.setEmail(dto.email());
        user.setEnabled(dto.enabled());
        user.setBorrowedBooksCount(dto.borrowedBooksCount());
        user.setPassword(dto.password());
        user.setRoles(userRoles);
        return user;
    }

}
