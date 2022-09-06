package com.example.springboot.jpa.service;

import com.example.springboot.jpa.data.dao.ProductDAO;
import com.example.springboot.jpa.data.dto.ProductDto;
import com.example.springboot.jpa.data.dto.ProductResponseDto;
import com.example.springboot.jpa.data.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductDAO productDAO;

    @Autowired
    ProductServiceImpl(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Override
    public ProductResponseDto getProduct(Long number) {
        Product product = productDAO.selectProduct(number);
        ProductResponseDto productResponseDto = new ProductResponseDto();

        productResponseDto.setNumber(product.getNumber());
        productResponseDto.setName(product.getName());
        productResponseDto.setPrice(product.getPrice());
        productResponseDto.setStock(product.getStock());

        return productResponseDto;
    }

    @Override
    public ProductResponseDto saveProduct(ProductDto productDto) {
        Product product = new Product();

        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setStock(productDto.getStock());

        Product savedProduct = productDAO.insertProduct(product);
        ProductResponseDto productResponseDto = new ProductResponseDto();

        productResponseDto.setNumber(savedProduct.getNumber());
        productResponseDto.setName(savedProduct.getName());
        productResponseDto.setPrice(savedProduct.getPrice());
        productResponseDto.setStock(savedProduct.getStock());

        return productResponseDto;
    }

    @Override
    public ProductResponseDto changeProductName(Long number, String name) throws Exception {
        Product selectProduct = productDAO.selectProduct(number);

        if (selectProduct.getNumber() == number) {
            ProductResponseDto productResponseDto = new ProductResponseDto();
            Product changedProduct = productDAO.updateProductName(number, name);

            productResponseDto.setNumber(changedProduct.getNumber());
            productResponseDto.setName(changedProduct.getName());
            productResponseDto.setPrice(changedProduct.getPrice());
            productResponseDto.setStock(changedProduct.getStock());

            return productResponseDto;
        } else {
            throw new Exception("해당 상품을 찾을 수 없습니다.");
        }
    }

    @Override
    public void deleteProduct(Long number) throws Exception {
        productDAO.deleteProduct(number);
    }
}
