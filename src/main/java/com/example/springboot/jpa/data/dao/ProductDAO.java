package com.example.springboot.jpa.data.dao;

import com.example.springboot.jpa.data.entity.Product;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

public interface ProductDAO {
    Optional<Product> insertProduct(Product product);

    Optional<Product> selectProduct(Long number) throws EntityNotFoundException;

    Optional<Product> updateProductName(Long number, String name);

    Optional<Boolean> deleteProduct(Long number);
}
