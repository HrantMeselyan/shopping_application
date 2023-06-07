package com.example.shopping_application.service.impl;

import com.example.shopping_application.entity.Cart;
import com.example.shopping_application.entity.CartItem;
import com.example.shopping_application.entity.Product;
import com.example.shopping_application.repository.CartItemRepository;
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
    private final CartItemRepository cartItemRepository;

    @Override
    public List<Cart> findAllByUser_id(int id) {
        return cartRepository.findAllByUserId(id);
    }

    @Override
    @Transactional
    public void save(int id, CurrentUser currentUser) {
        Optional<Cart> cartOptional = cartRepository.findAllByUser_Id(currentUser.getUser().getId());

        if (cartOptional.isPresent()) {
            Cart cart = cartOptional.get();
            List<CartItem> cartItems = cart.getCartItems();
            Optional<Product> productOptional = productRepository.findById(id);

            if (productOptional.isPresent()) {
                Product product = productOptional.get();
                CartItem existingCartItem = null;

                for (CartItem item : cartItems) {
                    if (item.getProduct().equals(product)) {
                        existingCartItem = item;
                        break;
                    }
                }

                if (existingCartItem != null) {
                    existingCartItem.setCount(existingCartItem.getCount() + 1);
                    cartItemRepository.save(existingCartItem);
                } else {
                    CartItem cartItem = new CartItem();
                    cartItem.setCount(1);
                    cartItem.setProduct(product);
                    cartItem.setCart(cart); // Set the cart relationship
                    cartItemRepository.save(cartItem);
                    cartItems.add(cartItem);
                }
            }

            cartRepository.save(cart);
        } else {
            Cart cart = new Cart();
            cart.setUser(currentUser.getUser());
            List<CartItem> cartItems = new ArrayList<>();
            Optional<Product> productOptional = productRepository.findById(id);

            if (productOptional.isPresent()) {
                Product product = productOptional.get();
                CartItem cartItem = new CartItem();
                cartItem.setCount(1);
                cartItem.setProduct(product);
                cartItem.setCart(cart); // Set the cart relationship
                cartItemRepository.save(cartItem);
                cartItems.add(cartItem);
            }

            cart.setCartItems(cartItems);
            cartRepository.save(cart);
        }
    }

    @Override
    @Transactional
    public void remove(int id, int productId) {
        cartRepository.deleteByUserIdAndCartItems_Product_Id(id, productId);

    }
}
