package com.example.shopping_application.service;

import com.example.shopping_application.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {
    List<User> findAll();

    void updatePicName(MultipartFile multipartFile, int id) throws IOException;
    void remove(int id);
    void save(User user);
    void update(User user, MultipartFile multipartFile) throws IOException;

    void removeById(int id);

    User findById(int id);
}
