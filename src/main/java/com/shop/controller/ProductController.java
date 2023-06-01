package com.shop.controller;

import com.shop.dto.ProductDTO;
import com.shop.entity.ProductEntity;
import com.shop.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public ResponseEntity<List<ProductDTO>> products() {
        return ResponseEntity.ok().body(productService.products());
    }

    @PostMapping("/")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductEntity product) {
        return ResponseEntity.ok().body(productService.addProduct(product));
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductEntity product, @PathVariable Long productId) {
        return productService.updateProduct(product, productId);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ProductDTO> removeProduct(@PathVariable Long productId) {
        return productService.removeProduct(productId);
    }

}
