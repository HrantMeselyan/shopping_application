package com.example.shopping_application.impl;

import com.example.shopping_application.entity.User;
import com.example.shopping_application.repository.UserRepository;
import com.example.shopping_application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Ashot Simonyan on 21.05.23.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Override
    public List<User> findAllCategory() {
        return userRepository.findAll();
    }

    @Override
    public void remove(int id) {
        userRepository.findById(id);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }
}
