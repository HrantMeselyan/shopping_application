package com.example.shopping_application.impl;

import com.example.shopping_application.entity.WishList;
import com.example.shopping_application.repository.WishListRepository;
import com.example.shopping_application.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Ashot Simonyan on 21.05.23.
 */
@Service
@RequiredArgsConstructor
public class WishListServiceImpl implements WishListService {

    private final WishListRepository wishListRepository;

    @Override
    public List<WishList> findAll() {
        return wishListRepository.findAll();
    }

    @Override
    public void remove(int id) {
        wishListRepository.deleteById(id);
    }

    @Override
    public void save(WishList wishList) {
        wishListRepository.save(wishList);
    }
}
