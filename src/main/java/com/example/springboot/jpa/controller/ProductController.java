package com.example.springboot.jpa.controller;

import com.example.springboot.jpa.data.dto.ChangeProductNameDto;
import com.example.springboot.jpa.data.dto.JsonDto;
import com.example.springboot.jpa.data.dto.ProductDto;
import com.example.springboot.jpa.data.dto.ProductResponseDto;
import com.example.springboot.jpa.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final HttpStatus successStatus = HttpStatus.OK;

    @Autowired
    ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<JsonDto<ProductResponseDto>> getProduct(@PathVariable("id") Long number) {
        ProductResponseDto productResponseDto = productService.getProduct(number);

        return ResponseEntity.status(successStatus).body(new JsonDto<>(true, "get product", successStatus, productResponseDto));
    }

    @PostMapping()
    public ResponseEntity<JsonDto<ProductResponseDto>> createProduct(@RequestBody ProductDto productDto) {
        ProductResponseDto productResponseDto = productService.saveProduct(productDto);

        return ResponseEntity.status(successStatus).body(new JsonDto<>(true, "create product", successStatus, productResponseDto));
    }

    @PutMapping()
    public ResponseEntity<JsonDto<ProductResponseDto>> changeProductName(@RequestBody ChangeProductNameDto changeProductNameDto) throws Exception {
        Long number = changeProductNameDto.getNumber();
        String name = changeProductNameDto.getName();

        ProductResponseDto productResponseDto = productService.changeProductName(number, name);

        return ResponseEntity.status(successStatus).body(new JsonDto<>(true, "change product name", successStatus, productResponseDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<JsonDto<ProductResponseDto>> deleteProduct(@PathVariable("id") Long number) throws Exception {
        productService.deleteProduct(number);

        return ResponseEntity.status(successStatus).body(new JsonDto<>(true, "delete product", successStatus, null));
    }
}
