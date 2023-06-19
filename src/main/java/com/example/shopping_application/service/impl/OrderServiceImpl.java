package com.example.shopping_application.service.impl;

import com.example.shopping_application.dto.orderDto.OrderResponseDto;
import com.example.shopping_application.entity.*;
import com.example.shopping_application.mapper.OrderMapper;
import com.example.shopping_application.repository.CartRepository;
import com.example.shopping_application.repository.OrderItemRepository;
import com.example.shopping_application.repository.OrderRepository;
import com.example.shopping_application.repository.UserRepository;
import com.example.shopping_application.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
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
    @Transactional
    public List<Order> findByUserId(int id) {
        return orderRepository.findAllByUserId(id);
    }

    @Override
    @Transactional
    public void removeByProductId(int id) {
        orderItemRepository.deleteByProduct_Id(id);
    }
    @Override
    @Transactional
    public void save(int userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        List<OrderItem> orderItems = new ArrayList<>();

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<Cart> cartList = cartRepository.findAllByUserId(userId);
            int totalAmount = 0;
            Order byUserIdAndStatus = orderRepository.findByUserIdAndStatus(userId, Status.PENDING);

            if (byUserIdAndStatus == null) {
                Order order = new Order();
                order.setUser(user);

                for (Cart cart : cartList) {
                    List<CartItem> cartItems = cart.getCartItems();

                    for (CartItem cartItem : cartItems) {
                        boolean found = false;

                        for (OrderItem orderItem : orderItems) {
                            if (orderItem.getProduct().getId() == cartItem.getProduct().getId()) {
                                orderItem.setCount(orderItem.getCount() + cartItem.getCount());
                                found = true;
                                break;
                            }
                        }

                        if (!found) {
                            OrderItem orderItem = new OrderItem();
                            orderItem.setCount(cartItem.getCount());
                            orderItem.setProduct(cartItem.getProduct());
                            orderItems.add(orderItem);
                        }

                        totalAmount += cartItem.getProduct().getPrice() * cartItem.getCount();
                    }
                }

                order.setOrderItems(orderItems);
                order.setTotalAmount(totalAmount);
                order.setStatus(Status.PENDING);
                orderRepository.save(order);
                cartRepository.deleteByUserId(userId);
            } else {
                Order existingOrder = byUserIdAndStatus;

                for (Cart cart : cartList) {
                    List<CartItem> cartItems = cart.getCartItems();

                    for (CartItem cartItem : cartItems) {
                        boolean found = false;

                        for (OrderItem orderItem : existingOrder.getOrderItems()) {
                            if (orderItem.getProduct().getId() == cartItem.getProduct().getId()) {
                                orderItem.setCount(orderItem.getCount() + cartItem.getCount());
                                found = true;
                                break;
                            }
                        }

                        if (!found) {
                            OrderItem orderItem = new OrderItem();
                            orderItem.setCount(cartItem.getCount());
                            orderItem.setProduct(cartItem.getProduct());
                            existingOrder.getOrderItems().add(orderItem);
                        }

                        totalAmount += cartItem.getProduct().getPrice() * cartItem.getCount();
                    }
                }

                existingOrder.setTotalAmount(existingOrder.getTotalAmount() + totalAmount);
                orderRepository.save(existingOrder);
                cartRepository.deleteByUserId(userId);
            }
        }
    }

}

