package com.example.shopping_application.service.impl;

import com.example.shopping_application.entity.Product;
import com.example.shopping_application.entity.WishList;
import com.example.shopping_application.repository.WishListRepository;
import com.example.shopping_application.service.WishListService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by Ashot Simonyan on 21.05.23.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class WishListServiceImpl implements WishListService {

    private final WishListRepository wishListRepository;

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
    public void removeByProductId(int id) {
        wishListRepository.deleteAllByProduct_Id(id);
    }

    @Override
    public void save(WishList wishList) {
        wishListRepository.save(wishList);
    }

    @Override
    public Optional<WishList> findByProduct(Product product) {
        return wishListRepository.findByProduct(product);
    }
}
