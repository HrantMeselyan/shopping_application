package com.example.shopping_application.service;

import com.example.shopping_application.entity.Product;

import java.io.IOException;
import java.util.List;

public interface MainService {
    byte[] getImage(String imageName) throws IOException;

    List<Product> search(String value);
}
