package com.example.voebb.service;

import com.example.voebb.model.dto.user.UserDTO;
import com.example.voebb.model.dto.user.UserRegistrationDTO;
import com.example.voebb.model.dto.user.UserUpdateDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;

import java.util.List;

public interface CustomUserService {
    List<UserDTO> getAllUsers();
    UserUpdateDTO getUserDTOByUsername(String username);

    void updateUserInfo(UserUpdateDTO userDto,
                        String oldEmail,
                        HttpServletRequest request,
                        HttpServletResponse response);

    UserDTO getUserById(Long id);
    UserDTO createUser(UserDTO userDto);
    UserDTO updateUser(Long id, UserDTO userDto);
    void enableUser(Long id);
    void disableUser(Long id);
    void deleteUser(Long id);
}
