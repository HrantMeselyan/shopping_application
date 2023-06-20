package com.example.shopping_application.service.impl;

import com.example.shopping_application.dto.orderDto.OrderResponseDto;
import com.example.shopping_application.entity.*;
import com.example.shopping_application.mapper.OrderMapper;
import com.example.shopping_application.repository.*;
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
    private final ProductRepository productRepository;


    @Override
    public List<OrderResponseDto> findAllOrder() {
        List<Order> all = orderRepository.findAll();
        return OrderMapper.findAll(all);
    }

    @Override
    @Transactional
    public Optional<Order> findByUserIdAndStatus(int id, Status status) {
        return orderRepository.findByUserIdAndStatus(id, status);
    }

    @Override
    @Transactional
    public void removeByProductIdAndOrderItemId(int productId, int orderItemId, int userId) {
        Optional<OrderItem> orderItemOptional = orderItemRepository.findById(orderItemId);
        Optional<Product> productOptional = productRepository.findById(productId);

        if (orderItemOptional.isPresent() && productOptional.isPresent()) {
            OrderItem orderItem = orderItemOptional.get();
            Product product = productOptional.get();

            int count = orderItem.getCount();
            product.setCount(product.getCount() + count);

            Optional<Order> orderOptional = orderRepository.findByUserIdAndStatus(userId, Status.PENDING);
            if (orderOptional.isPresent()) {
                Order order = orderOptional.get();

                double itemPrice = orderItem.getProduct().getPrice();
                double removedAmount = itemPrice * count;

                double totalAmount = order.getTotalAmount() - removedAmount;
                order.setTotalAmount(totalAmount);

                orderItemRepository.deleteByProduct_IdAndId(productId, orderItemId);
            }
        }
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
                OrderItem orderItem = new OrderItem();
                orderItem.setCount(cartItem.getCount());
                orderItem.setProduct(cartItem.getProduct());
                orderItems.add(orderItem);
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

