package com.example.shopping_application.impl;

import com.example.shopping_application.entity.Delivery;
import com.example.shopping_application.repository.DeliveryRepository;
import com.example.shopping_application.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Ashot Simonyan on 21.05.23.
 */
@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository deliveryRepository;

    @Override
    public List<Delivery> findAllCategory() {
        return deliveryRepository.findAll();
    }

    @Override
    public void remove(int id) {
        deliveryRepository.deleteById(id);
    }

    @Override
    public void save(Delivery delivery) {
        deliveryRepository.save(delivery);
    }
}
