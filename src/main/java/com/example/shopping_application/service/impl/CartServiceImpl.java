package com.example.shopping_application.service.impl;

import com.example.shopping_application.dto.cartDto.CartDto;
import com.example.shopping_application.entity.Cart;
import com.example.shopping_application.entity.CartItem;
import com.example.shopping_application.entity.Product;
import com.example.shopping_application.mapper.CartMapper;
import com.example.shopping_application.mapper.UserMapper;
import com.example.shopping_application.repository.CartItemRepository;
import com.example.shopping_application.repository.CartRepository;
import com.example.shopping_application.repository.ProductRepository;
import com.example.shopping_application.security.CurrentUser;
import com.example.shopping_application.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<CartDto> findAllByUser_id(int id) {
        List<Cart> allByUserId = cartRepository.findAllByUserId(id);
        return CartMapper.findAllByUser_id(allByUserId);

    }

    @Override
    @Transactional
    public void save(int id, CurrentUser currentUser) {
        Optional<Cart> cartOptional = cartRepository.findAllByUser_Id(currentUser.getUser().getId());
        Cart cart;
        if (cartOptional.isPresent()) {
            cart = cartOptional.get();
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
                    existingCartItem.getProduct().setCount(product.getCount() - 1);
                    cartItemRepository.save(existingCartItem);
                } else {
                    product.setCount(product.getCount() - 1);
                    productRepository.save(product);

                    CartItem cartItem = new CartItem();
                    cartItem.setCount(1);
                    cartItem.setProduct(product);
                    cartItem.setCart(cart);
                    cartItemRepository.save(cartItem);
                    cartItems.add(cartItem);
                }
            }

        } else {
            cart = new Cart();
            cart.setUser(UserMapper.currentUserToUser(currentUser));
            List<CartItem> cartItems = new ArrayList<>();
            Optional<Product> productOptional = productRepository.findById(id);

            if (productOptional.isPresent()) {
                Product product = productOptional.get();
                product.setCount(product.getCount() - 1);
                productRepository.save(product);
                CartItem cartItem = new CartItem();
                cartItem.setCount(1);
                cartItem.setProduct(product);
                cartItem.setCart(cart);
                cartItemRepository.save(cartItem);
                cartItems.add(cartItem);
            }

            cart.setCartItems(cartItems);
        }
        cartRepository.save(cart);
    }

    @Override
    @Transactional
    public void remove(int cartId, int productId, int count) {
        Optional<Cart> byId = cartRepository.findAllByUser_Id(cartId);
        Optional<Product> productById = productRepository.findById(productId);
        if (productById.isPresent()) {
            Product product = productById.get();
            product.setCount(product.getCount() + count);
        }
        cartItemRepository.deleteByCart_IdAndProduct_Id(byId.get().getId(), productId);
    }


    @Override
    @Transactional
    public boolean updateCartItemCounts(List<Integer> cartItemIds, List<Integer> counts) {
        boolean isValid = false;
        for (int i = 0; i < cartItemIds.size(); i++) {
            if (counts.get(i) == null || counts.get(i) <= 0) {
                return isValid;
            }
            Optional<CartItem> byId = cartItemRepository.findById(cartItemIds.get(i));
            CartItem cartItem = byId.get();
            Optional<Product> productOptional = productRepository.findById(cartItem.getProduct().getId());
            Product product = productOptional.get();
            if (product.getCount() - counts.get(i) + cartItem.getCount() < 0) {
                return isValid;
            }
            product.setCount((product.getCount() - counts.get(i)) + cartItem.getCount());
            cartItem.setCount(counts.get(i));
            isValid = true;
        }
        return isValid;
    }
}
