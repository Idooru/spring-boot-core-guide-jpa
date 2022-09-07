package com.example.springboot.jpa.service;

import com.example.springboot.jpa.data.dto.ProductDto;
import com.example.springboot.jpa.data.dto.ProductResponseDto;

import java.util.Optional;

public interface ProductService {

    Optional<ProductResponseDto> getProduct(Long number);

    Optional<ProductResponseDto> saveProduct(ProductDto productDto);

    Optional<ProductResponseDto> changeProductName(Long number, String name);

    Optional<Boolean> deleteProduct(Long number);

}
