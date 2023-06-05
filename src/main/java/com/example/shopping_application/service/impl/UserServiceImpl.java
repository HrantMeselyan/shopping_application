package com.example.shopping_application.service.impl;

import com.example.shopping_application.entity.User;
import com.example.shopping_application.repository.UserRepository;
import com.example.shopping_application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

/**
 * Created by Ashot Simonyan on 21.05.23.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Value("${shopping-app.upload.image.path}")
    private String imageUploadPath;

    @Override
    public void remove(int id) {
        userRepository.findById(id);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void updatePicName(MultipartFile multipartFile, User user) throws IOException {
        deleteProfilePicture(user);
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String fileName = System.nanoTime() + "_" + multipartFile.getOriginalFilename();
            File file = new File(imageUploadPath + fileName);
            multipartFile.transferTo(file);

            user.setProfilePic(fileName);
            userRepository.save(user);
        }
    }

    @Override
    public void update(User user, MultipartFile multipartFile) throws IOException {
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String fileName = System.nanoTime() + "_" + multipartFile.getOriginalFilename();
            File file = new File(imageUploadPath + fileName);
            multipartFile.transferTo(file);
            user.setProfilePic(fileName);
        }
        userRepository.save(user);
    }

    @Override
    public void removeById(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findById(int id) {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            return byId.get();
        }
        return null;
    }

    @Override
    public User findByIdWithAddresses(int id) {
        Optional<User> byId = userRepository.findById(id);
        return byId.get();
    }

    private void deleteProfilePicture(User user) {
        String existingProfilePic = user.getProfilePic();
        if (existingProfilePic != null) {
            try {
                Path path = Paths.get(imageUploadPath + existingProfilePic);
                Files.deleteIfExists(path);
            } catch (IOException e) {
                System.out.println("Failed to delete the existing profile picture: " + e.getMessage());
            }
        }
    }
}
