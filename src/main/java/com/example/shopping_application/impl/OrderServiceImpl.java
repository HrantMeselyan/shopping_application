package com.example.shopping_application.impl;

import com.example.shopping_application.entity.Order;
import com.example.shopping_application.repository.OrderRepository;
import com.example.shopping_application.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Ashot Simonyan on 21.05.23.
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;


    @Override
    public List<Order> findAllCategory() {
        return orderRepository.findAll();
    }

    @Override
    public void remove(int id) {
        orderRepository.deleteById(id);
    }

    @Override
    public void save(Order order) {
        orderRepository.save(order);
    }
}
