package com.example.shopping_application.service.impl;

import com.example.shopping_application.entity.Delivery;
import com.example.shopping_application.entity.Order;
import com.example.shopping_application.entity.Status;
import com.example.shopping_application.repository.DeliveryRepository;
import com.example.shopping_application.repository.OrderRepository;
import com.example.shopping_application.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    public List<Delivery> findAllCategory() {
        return deliveryRepository.findAll();
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
}
