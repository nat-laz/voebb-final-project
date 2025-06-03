package com.example.voebb.service;

import com.example.voebb.model.dto.ItemActivityDTO;
import com.example.voebb.model.dto.user.UserDTO;
import com.example.voebb.model.dto.user.UserUpdateDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface CustomUserService {
    List<UserDTO> getAllUsers();
    UserUpdateDTO getUserUpdateDTOByUsername(String username);
    UserDTO getUserDTOByUsername(String username);
    List<ItemActivityDTO> getItemActivitiesByUsername(String username);
    public Boolean isBorrowingExpiresSoon(String username);
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
    boolean emailExists(String email);
    boolean phoneNumberExists(String email);
}
