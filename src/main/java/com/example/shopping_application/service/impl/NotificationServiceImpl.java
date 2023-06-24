package com.example.shopping_application.service.impl;

import com.example.shopping_application.dto.notificationDto.NotificationRequestDto;
import com.example.shopping_application.dto.notificationDto.NotificationResponseDto;
import com.example.shopping_application.entity.Notification;
import com.example.shopping_application.entity.User;
import com.example.shopping_application.mapper.NotificationMapper;
import com.example.shopping_application.repository.NotificationRepository;
import com.example.shopping_application.repository.UserRepository;
import com.example.shopping_application.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by Ashot Simonyan on 21.05.23.
 */
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;


    @Override
    public List<NotificationResponseDto> findAllByUserId(int id) {
        List<Notification> allByUserId = notificationRepository.findAllByUser_Id(id);
        return NotificationMapper.map(allByUserId);
    }

    @Override
    public void remove(int id) {
        notificationRepository.deleteById(id);
    }

    @Override
    public void save(NotificationRequestDto notificationRequestDto) {
        Optional<User> userOptional = userRepository.findByEmail(notificationRequestDto.getEmail());
        if (userOptional.isPresent()) {
            Notification notification = NotificationMapper.map(notificationRequestDto);
            notification.setUser(userOptional.get());
            notificationRepository.save(notification);
        }
    }
}
