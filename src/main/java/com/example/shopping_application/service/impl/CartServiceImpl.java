package com.example.shopping_application.service.impl;

import com.example.shopping_application.entity.Cart;
import com.example.shopping_application.entity.Product;
import com.example.shopping_application.repository.CartRepository;
import com.example.shopping_application.repository.ProductRepository;
import com.example.shopping_application.security.CurrentUser;
import com.example.shopping_application.service.CartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    @Override
    public List<Cart> findAllByUser_id(int id) {
        return cartRepository.findAllByUserId(id);
    }

    @Override
    public void save(int id, CurrentUser currentUser) {
        Cart cart = new Cart();
        cart.setUser(currentUser.getUser());
        Optional<Product> byId = productRepository.findById(id);
        List<Product> productList = new ArrayList<>();
        productList.add(byId.get());
        cart.setProducts(productList);
        cartRepository.save(cart);
    }

    @Override
    @Transactional
    public void remove(int id, int productId) {
        cartRepository.deleteByUserIdAndProducts_Id(id, productId);
    }
}
