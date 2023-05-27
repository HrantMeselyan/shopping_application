package com.example.shopping_application.service;

import com.example.shopping_application.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {

    List<User> findAllUser();
    void remove(int id);
    void save(User user);

    User findAllById(int id);

    void updatePicName(MultipartFile multipartFile, int id) throws IOException;
}
