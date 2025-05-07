package com.example.voebb.service.impl;

import com.example.voebb.exception.UserNotFoundException;
import com.example.voebb.model.dto.user.UserDTO;
import com.example.voebb.model.entity.CustomUser;
import com.example.voebb.repository.CustomUserRepo;
import com.example.voebb.service.CustomUserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserServiceImpl implements CustomUserService {

    @Autowired
    private CustomUserRepo userRepo;

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
    @Transactional  // Ensure the method is wrapped in a transaction
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
