package com.example.shopping_application.service.impl;


import com.example.shopping_application.repository.AddressRepository;
import com.example.shopping_application.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;

    @Override
    public void delete(int id) {
        addressRepository.deleteById(id);
    }
}