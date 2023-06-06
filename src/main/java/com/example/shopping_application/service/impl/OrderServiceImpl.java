package com.example.shopping_application.service.impl;

import com.example.shopping_application.entity.Order;
import com.example.shopping_application.entity.OrderItem;
import com.example.shopping_application.entity.Product;
import com.example.shopping_application.entity.User;
import com.example.shopping_application.repository.*;
import com.example.shopping_application.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

import static java.time.LocalTime.now;

/**
 * Created by Ashot Simonyan on 21.05.23.
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final OrderItemRepository orderItemRepository;


    @Override
    public List<Order> findAllOrder() {
        return orderRepository.findAll();
    }

    @Override
    public void remove(int id) {
        orderRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void save(int userId, List<Integer> productsId) {
        Order order = new Order();
        List<OrderItem> orderItemList = new ArrayList<>();
        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {
            LocalDateTime dateTime = LocalDateTime.now();
            order.setDateTime(dateTime);
            order.setUser(user.get());
            order.setTotalAmount(4.5);

            Set<Product> uniqueProducts = new HashSet<>();
            Map<Integer, Integer> productCounts = new HashMap<>();

            for (Integer productId : productsId) {
                Optional<Product> productOptional = productRepository.findById(productId);
                productOptional.ifPresent(uniqueProducts::add);

                productCounts.put(productId, productCounts.getOrDefault(productId, 0) + 1);
            }

            for (Product product : uniqueProducts) {
                OrderItem orderItem = new OrderItem();
                orderItem.setProduct(product);
                orderItem.setCount(productCounts.get(product.getId()));
                orderItemRepository.save(orderItem);
                orderItemList.add(orderItem);
            }

            order.setOrderItems(orderItemList);
            orderRepository.save(order);
            cartRepository.deleteByUserId(userId);
        }
    }
}
