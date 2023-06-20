package com.example.shopping_application.service;

import com.example.shopping_application.dto.userDto.UserRegisterDto;
import com.example.shopping_application.entity.User;
import com.example.shopping_application.security.CurrentUser;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {
    List<User> findAll();

    void updateUser(MultipartFile multipartFile, User user, CurrentUser currentUser) throws IOException;

    void remove(int id);

    void save(User user);

    void removeById(int id);

    User findById(int id);

    User findByIdWithAddresses(int id);

    User setUserEncodedPassword(UserRegisterDto userRegisterDto);
}
