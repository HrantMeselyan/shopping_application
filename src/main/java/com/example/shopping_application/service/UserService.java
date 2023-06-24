package com.example.shopping_application.service;

import com.example.shopping_application.dto.userDto.UpdatePasswordDto;
import com.example.shopping_application.dto.userDto.UserRegisterDto;
import com.example.shopping_application.entity.User;
import com.example.shopping_application.security.CurrentUser;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface UserService {

    User findByEmail(String email);

    List<User> findAll();

    void updateUser(MultipartFile multipartFile, User user, CurrentUser currentUser) throws IOException;

    boolean changeUserPasswordTokenVerify(String email, String token);

    boolean changePassword(String password, String password2, String email, String token);

    void remove(int id);

    User save(UserRegisterDto user);
    User save(User user);

    void removeById(int id);

    User findById(int id);

    User findByIdWithAddresses(int id);

    void updatePassword(User user, UpdatePasswordDto updatePasswordDto);

    boolean verifyUserByEmail(String email, UUID token);

}
