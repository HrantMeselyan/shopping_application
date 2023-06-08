package com.example.shopping_application.service.impl;

import com.example.shopping_application.entity.Product;
import com.example.shopping_application.entity.User;
import com.example.shopping_application.entity.WishList;
import com.example.shopping_application.mapper.UserMapper;
import com.example.shopping_application.repository.ProductRepository;
import com.example.shopping_application.repository.WishListRepository;
import com.example.shopping_application.security.CurrentUser;
import com.example.shopping_application.service.WishListService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Created by Ashot Simonyan on 21.05.23.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class WishListServiceImpl implements WishListService {

    private final WishListRepository wishListRepository;
    private final ProductRepository productRepository;

    @Override
    public List<WishList> findAll() {
        return wishListRepository.findAll();
    }

    @Override
    public Optional<WishList> findByUserId(int id) {
        return wishListRepository.findByUserId(id);
    }

    @Override
    public void remove(int id) {
        wishListRepository.deleteById(id);
    }

    @Override
    public void remove(int id, CurrentUser currentUser) {
        User user = UserMapper.currentUserToUser(currentUser);
        Optional<WishList> byUserId = wishListRepository.findByUserId(user.getId());
        if (byUserId.isPresent()) {
            Optional<Product> byId = productRepository.findById(id);
            if (byId.isPresent()) {
                WishList wishList = byUserId.get();
                Set<Product> product = wishList.getProduct();
                product.remove(byId.get());
                wishList.setProduct(product);
                wishListRepository.save(wishList);
            }
        }
    }


    @Override
    public void save(WishList wishList) {
        wishListRepository.save(wishList);
    }

    @Override
    public Optional<WishList> findByProduct(Product product) {
        return wishListRepository.findByProduct(product);
    }

    @Override
    public void save(int productId, CurrentUser currentUser) {
        Optional<Product> byId = productRepository.findById(productId);
        if (byId.isPresent()) {
            User user = UserMapper.currentUserToUser(currentUser);
            Optional<WishList> byUserId = wishListRepository.findByUserId(user.getId());
            if (byUserId.isEmpty()) {
                Set<Product> products = new HashSet<>();
                products.add(byId.get());
                WishList wishList = new WishList();
                wishList.setUser(user);
                wishList.setProduct(products);
                wishListRepository.save(wishList);
            } else {
                WishList wishList = byUserId.get();
                Set<Product> productset = wishList.getProduct();
                productset.add(byId.get());
                wishList.setProduct(productset);
                wishListRepository.save(wishList);
            }
        }
    }
}
