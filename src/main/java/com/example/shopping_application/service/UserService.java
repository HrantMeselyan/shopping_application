package com.example.shopping_application.service;

import com.example.shopping_application.dto.userDto.UpdatePasswordDto;
import com.example.shopping_application.dto.userDto.UserRegisterDto;
import com.example.shopping_application.entity.User;
import com.example.shopping_application.security.CurrentUser;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface UserService {
    List<User> findAll();

    void updateUser(MultipartFile multipartFile, User user, CurrentUser currentUser) throws IOException;

    void remove(int id);

    User save(UserRegisterDto user);

    void removeById(int id);

    User findById(int id);

    User findByIdWithAddresses(int id);

    void updatePassword(User user, UpdatePasswordDto updatePasswordDto);

    boolean verifyUserByEmail(String email, UUID token);
}
