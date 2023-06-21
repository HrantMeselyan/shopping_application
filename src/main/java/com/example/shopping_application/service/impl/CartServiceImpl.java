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
    public CartDto findAllByUser_id(int id) {
        Optional<Cart> allByUserId = cartRepository.findAllByUser_Id(id);
        if (allByUserId.isPresent()) {
            Cart cart = allByUserId.get();
            return CartMapper.convertToDto(cart);
        }
        return null;
    }

    @Override
    @Transactional
    public void save(int id, CurrentUser currentUser) {
        Optional<Cart> cartOptional = cartRepository.findAllByUser_Id(currentUser.getUser().getId());
        Cart cart = cartOptional.orElseGet(() -> createNewCart(currentUser));

        List<CartItem> cartItems = cart.getCartItems();
        Optional<Product> productOptional = productRepository.findById(id);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            CartItem existingCartItem = findExistingCartItem(cartItems, product);

            if (existingCartItem != null) {
                incrementCartItem(existingCartItem);
            } else {
                product.setCount(product.getCount() - 1);
                productRepository.save(product);

                CartItem cartItem = createCartItem(product, cart);
                cartItems.add(cartItem);
            }

            cartItemRepository.saveAll(cartItems);
            productRepository.save(product);
        }

        cartRepository.save(cart);
    }

    private Cart createNewCart(CurrentUser currentUser) {
        Cart cart = new Cart();
        cart.setUser(UserMapper.currentUserToUser(currentUser));
        cart.setCartItems(new ArrayList<>());
        return cart;
    }

    private CartItem findExistingCartItem(List<CartItem> cartItems, Product product) {
        return cartItems.stream()
                .filter(item -> item.getProduct().equals(product))
                .findFirst()
                .orElse(null);
    }

    private void incrementCartItem(CartItem cartItem) {
        cartItem.setCount(cartItem.getCount() + 1);
        cartItem.getProduct().setCount(cartItem.getProduct().getCount() - 1);
    }

    private CartItem createCartItem(Product product, Cart cart) {
        CartItem cartItem = new CartItem();
        cartItem.setCount(1);
        cartItem.setProduct(product);
        cartItem.setCart(cart);
        return cartItem;
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
