package com.example.shopping_application.service.impl;

import com.example.shopping_application.dto.orderDto.OrderResponseDto;
import com.example.shopping_application.entity.*;
import com.example.shopping_application.mapper.OrderMapper;
import com.example.shopping_application.repository.CartRepository;
import com.example.shopping_application.repository.OrderItemRepository;
import com.example.shopping_application.repository.OrderRepository;
import com.example.shopping_application.repository.UserRepository;
import com.example.shopping_application.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Ashot Simonyan on 21.05.23.
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final OrderItemRepository orderItemRepository;


    @Override
    public List<OrderResponseDto> findAllOrder() {
        List<Order> all = orderRepository.findAll();
        return OrderMapper.findAll(all);
    }

    @Override
    public Order findByUserId(int id) {
        Optional<Order> byUserId = orderRepository.findByUserId(id);
        return byUserId.orElse(null);
    }

    @Override
    public void remove(int id) {
        orderRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void save(int userId) {
        Optional<User> user = userRepository.findById(userId);
        List<Cart> cartList = cartRepository.findAllByUserId(userId);
        int totalAmount = 0;

        Order order = new Order();
        order.setUser(user.get());
        order.setOrderItems(new ArrayList<>());

        for (Cart cart : cartList) {
            List<CartItem> cartItems = cart.getCartItems();
            for (CartItem cartItem : cartItems) {
                OrderItem orderItem = new OrderItem();
                totalAmount += cartItem.getProduct().getPrice() * cartItem.getCount();
                orderItem.setCount(cartItem.getCount());
                orderItem.setProduct(cartItem.getProduct());
                orderItem.setOrder(order);
                orderItemRepository.save(orderItem);
            }
        }
        order.setTotalAmount(totalAmount);
        orderRepository.save(order);
        cartRepository.deleteByUserId(userId);
    }

}

