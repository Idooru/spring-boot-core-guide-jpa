package com.example.springboot.jpa.service;

import com.example.springboot.jpa.data.dao.ProductDAO;
import com.example.springboot.jpa.data.dto.ProductDto;
import com.example.springboot.jpa.data.dto.ProductResponseDto;
import com.example.springboot.jpa.data.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductDAO productDAO;
    private Product product;

    @Autowired
    ProductServiceImpl(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Override
    public Optional<ProductResponseDto> getProduct(Long number) {
        Optional<Product> selectedProduct = productDAO.selectProduct(number);

        if (selectedProduct.isEmpty()) {
            throw new EntityNotFoundException();
        }

        selectedProduct.ifPresent(value -> product = value);

        return this.createProductResponseDto();
    }

    @Override
    public Optional<ProductResponseDto> saveProduct(ProductDto productDto) {
        Product product = new Product();

        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setStock(productDto.getStock());
        product.setCreatedAt(LocalDateTime.now());

        Optional<Product> insertedProduct = productDAO.insertProduct(product);
        insertedProduct.ifPresent(value -> this.product = value);

        return this.createProductResponseDto();
    }

    @Override
    public Optional<ProductResponseDto> changeProductName(Long number, String name) {
        Optional<Product> selectedProduct = productDAO.selectProduct(number);

        if (selectedProduct.isEmpty()) {
            throw new EntityNotFoundException();
        }

        selectedProduct.ifPresent(value -> this.product = value);

        Optional<Product> changedProduct = productDAO.updateProductName(number, name);
        changedProduct.ifPresent(value -> this.product = value);

        return this.createProductResponseDto();
    }

    @Override
    public Optional<Boolean> deleteProduct(Long number) {
        Optional<Product> selectedProduct = productDAO.selectProduct(number);

        if (selectedProduct.isEmpty()) {
            throw new EntityNotFoundException();
        }

        selectedProduct.ifPresent(value -> this.product = value);

        return productDAO.deleteProduct(number);
    }

    public Optional<ProductResponseDto> createProductResponseDto() {
        ProductResponseDto productResponseDto = new ProductResponseDto();

        productResponseDto.setNumber(this.product.getNumber());
        productResponseDto.setName(this.product.getName());
        productResponseDto.setPrice(this.product.getPrice());
        productResponseDto.setStock(this.product.getStock());

        return Optional.of(productResponseDto);
    }
}
