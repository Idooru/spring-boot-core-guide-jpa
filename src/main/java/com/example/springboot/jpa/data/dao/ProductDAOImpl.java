package com.example.springboot.jpa.data.dao;

import com.example.springboot.jpa.data.entity.Product;
import com.example.springboot.jpa.data.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class ProductDAOImpl implements ProductDAO {

    private final ProductRepository productRepository;

    @Autowired
    public ProductDAOImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Optional<Product> insertProduct(Product product) {
        try {
            Product savedProduct = productRepository.save(product);

            return Optional.of(savedProduct);
        } catch (Exception err) {
            System.out.println("상품을 추가하는데 실패하였습니다.");
            System.out.println(err.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Product> selectProduct(Long number) {
        try {
            Product selectedProduct = productRepository.findById(number).orElseThrow(EntityNotFoundException::new);

            return Optional.of(selectedProduct);
        } catch (EntityNotFoundException err) {
            System.out.println("상품을 찾는데 실패하였습니다.");
            System.out.println(err.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Product> updateProductName(Long number, String name) {
        if (this.selectProduct(number).isPresent()) {
            Product product = this.selectProduct(number).get();

            product.setName(name);
            product.setUpdatedAt(LocalDateTime.now());

            try {
                Product updatedProduct = productRepository.save(product);
                return Optional.of(updatedProduct);
            } catch (Exception err) {
                System.out.println("상품을 수정하는데 실패하였습니다.");
                System.out.println(err.getMessage());
                return Optional.empty();
            }

        } else {
            return Optional.empty();
        }

    }

    @Override
    public Optional<Boolean> deleteProduct(Long number) {
        if (this.selectProduct(number).isPresent()) {
            Product product = this.selectProduct(number).get();

            try {
                productRepository.delete(product);
                return Optional.of(true);
            } catch (Exception err) {
                System.out.println("상품을 삭제하는데 실패하였습니다.");
                System.out.println(err.getMessage());
                return Optional.empty();
            }

        } else {
            return Optional.empty();
        }

    }
}