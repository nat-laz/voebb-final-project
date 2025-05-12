package com.example.voebb.service;

import com.example.voebb.model.dto.user.UserDTO;

import java.util.List;

public interface CustomUserService {
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long id);
    UserDTO createUser(UserDTO userDto);
    UserDTO updateUser(Long id, UserDTO userDto);
    void enableUser(Long id);
    void disableUser(Long id);
    void deleteUser(Long id);
}
