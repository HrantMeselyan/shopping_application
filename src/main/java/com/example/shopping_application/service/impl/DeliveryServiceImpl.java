package com.example.shopping_application.service.impl;

import com.example.shopping_application.entity.Delivery;
import com.example.shopping_application.entity.Order;
import com.example.shopping_application.entity.Status;
import com.example.shopping_application.entity.User;
import com.example.shopping_application.repository.DeliveryRepository;
import com.example.shopping_application.repository.OrderRepository;
import com.example.shopping_application.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by Ashot Simonyan on 21.05.23.
 */
@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final OrderRepository orderRepository;


    @Override
    public Page<Delivery> findAllByUserIdAndOrderStatus(int id, Status status, Pageable pageable) {
        return deliveryRepository.findAllByUserIdAndOrderStatus(id,status,pageable);
    }

    @Override
    public Page<Delivery> findAllByOrderStatus(Status status,Pageable pageable) {
        return deliveryRepository.findAllByOrderStatus(status,pageable);
    }

    @Override
    public Optional<Delivery> findById(int id) {
        return deliveryRepository.findById(id);
    }

    @Override
    public void remove(int id) {
        deliveryRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void save(int id) {
        Optional<Order> byId = orderRepository.findById(id);
        Order order = byId.orElse(null);
        Delivery delivery = new Delivery();
        delivery.setOrder(order);
        deliveryRepository.save(delivery);
        order.setStatus(Status.APPROVED);
    }

    @Override
    public void save(Delivery delivery) {
        deliveryRepository.save(delivery);
    }

    @Override
    public void chooseDelivery(User user, int id,Status status) {
        Delivery delivery = deliveryRepository.findById(id).orElse(null);
        delivery.setUser(user);
        delivery.getOrder().setStatus(status);
        deliveryRepository.save(delivery);
    }
}
