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

import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final HttpStatus successStatus = HttpStatus.OK;
    private final HttpStatus failedStatus = HttpStatus.BAD_REQUEST;
    private ProductResponseDto productResponseDto;

    @Autowired
    ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<JsonDto<ProductResponseDto>> getProduct(@PathVariable("id") Long number) {
        Optional<ProductResponseDto> optionalProductResponseDto = productService.getProduct(number);

        if (optionalProductResponseDto.isEmpty()) {
            return ResponseEntity.status(failedStatus).body(
                    new JsonDto<>(false, "failed to get product", failedStatus, null));
        }

        optionalProductResponseDto.ifPresent(value -> productResponseDto = value);

        return ResponseEntity.status(successStatus).body(
                new JsonDto<>(true, "get product", successStatus, productResponseDto));
    }

    @PostMapping()
    public ResponseEntity<JsonDto<ProductResponseDto>> createProduct(@RequestBody ProductDto productDto) {
        Optional<ProductResponseDto> optionalProductResponseDto = productService.saveProduct(productDto);

        if (optionalProductResponseDto.isEmpty()) {
            return ResponseEntity.status(failedStatus).body(
                    new JsonDto<>(false, "failed to create product", failedStatus, null));
        }

        optionalProductResponseDto.ifPresent(value -> productResponseDto = value);

        return ResponseEntity.status(successStatus).body(
                new JsonDto<>(true, "create product", successStatus, productResponseDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JsonDto<ProductResponseDto>> changeProductName(
            @RequestBody ChangeProductNameDto changeProductNameDto, @PathVariable("id") Long number) {
        String name = changeProductNameDto.getName();

        Optional<ProductResponseDto> optionalProductResponseDto = productService.changeProductName(number, name);

        if (optionalProductResponseDto.isEmpty()) {
            return ResponseEntity.status(failedStatus).body(
                    new JsonDto<>(false, "failed to change product name", failedStatus, null));
        }

        optionalProductResponseDto.ifPresent(value -> productResponseDto = value);

        return ResponseEntity.status(successStatus).body(new JsonDto<>(true, "change product name", successStatus, productResponseDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<JsonDto<ProductResponseDto>> deleteProduct(@PathVariable("id") Long number) {
        Optional<Boolean> optionalDeleteResult = productService.deleteProduct(number);

        if (optionalDeleteResult.isEmpty()) {
            return ResponseEntity.status(failedStatus).body(
                    new JsonDto<>(false, "failed to delete product", failedStatus, null));
        }

        return ResponseEntity.status(successStatus).body(new JsonDto<>(true, "delete product", successStatus, null));
    }
}
