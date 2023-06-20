package com.example.shopping_application.service.impl;

import com.example.shopping_application.dto.userDto.UpdatePasswordDto;
import com.example.shopping_application.dto.userDto.UserRegisterDto;
import com.example.shopping_application.entity.Role;
import com.example.shopping_application.entity.User;
import com.example.shopping_application.mapper.UserMapper;
import com.example.shopping_application.repository.UserRepository;
import com.example.shopping_application.security.CurrentUser;
import com.example.shopping_application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Created by Ashot Simonyan on 21.05.23.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
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
    @Transactional
    public void updateUser(MultipartFile multipartFile, User user, CurrentUser currentUser) throws IOException {
        User userOldData = currentUser.getUser();
        if (user.getName() == null || user.getName().equals("")) {
            user.setName(userOldData.getName());
        }
        if (user.getSurname() == null || user.getSurname().equals("")) {
            user.setSurname(userOldData.getSurname());
        }
        if (user.getEmail() == null || user.getEmail().equals("")) {
            user.setEmail(userOldData.getEmail());
        }
        if (user.getPhoneNumber() == null || user.getPhoneNumber().equals("")) {
            user.setPhoneNumber(userOldData.getPhoneNumber());
        }
        if (user.getGender() == null) {
            user.setGender(userOldData.getGender());
        }
        if (user.getRole() == null) {
            user.setRole(userOldData.getRole());
        }
        if (userOldData.getProfilePic() != null) {
            user.setProfilePic(userOldData.getProfilePic());
        }
        user.setId(userOldData.getId());
        user.setPassword(userOldData.getPassword());
        user.setPostCode(userOldData.getPostCode());
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

    @Override
    public User setUserEncodedPassword(UserRegisterDto userRegisterDto) {
        User user = UserMapper.userRegisterDtoToUser(userRegisterDto);
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setRole(Role.USER);
        return user;
    }

    @Override
    public void updatePassword(User user, UpdatePasswordDto updatePasswordDto) {
        if (passwordEncoder.matches(updatePasswordDto.getOldPassword(), user.getPassword()) && updatePasswordDto.getPassword1().equals(updatePasswordDto.getPassword2())) {
            String encodedPassword = passwordEncoder.encode(updatePasswordDto.getPassword2());
            user.setPassword(encodedPassword);
            userRepository.save(user);
        }
    }

    public void deleteProfilePicture(String existingProfilePic) {
        if (existingProfilePic != null) {
            File file = new File(imageUploadPath + existingProfilePic);
            if (file.exists()) {
                boolean delete = file.delete();
                System.out.println(delete);
            }
        }
    }
}
