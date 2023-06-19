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

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Optional<Order> byUserIdAndStatus = orderRepository.findByUserIdAndStatus(userId, Status.PENDING);

            if (byUserIdAndStatus.isEmpty()) {
                Order order = createNewOrder(user, userId);
                orderRepository.save(order);
            } else {
                Order existingOrder = byUserIdAndStatus.get();
                updateExistingOrder(existingOrder, userId);
                orderRepository.save(existingOrder);
            }
            cartRepository.deleteByUserId(userId);
        }
    }

    private Order createNewOrder(User user, int userId) {
        Optional<Cart> allByUserId = cartRepository.findAllByUser_Id(userId);
        List<OrderItem> orderItems = new ArrayList<>();
        int totalAmount = 0;

        if (allByUserId.isPresent()) {
            Cart cart = allByUserId.get();
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

        Order order = new Order();
        order.setUser(user);
        order.setOrderItems(orderItems);
        order.setTotalAmount(totalAmount);
        order.setStatus(Status.PENDING);

        return order;
    }

    private void updateExistingOrder(Order existingOrder, int userId) {
        Optional<Cart> allByUserId = cartRepository.findAllByUser_Id(userId);

        if (allByUserId.isPresent()) {
            Cart cart = allByUserId.get();
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

                existingOrder.setTotalAmount(existingOrder.getTotalAmount() + (cartItem.getProduct().getPrice() * cartItem.getCount()));
            }
        }
    }

}

