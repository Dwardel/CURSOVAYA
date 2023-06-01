package com.shop.service;

import com.shop.dto.ProductDTO;
import com.shop.entity.CartEntity;
import com.shop.entity.ProductEntity;
import com.shop.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDTO> products() {
        return productRepository.findAll().stream().map(ProductDTO::toProductDto).toList();
    }

    public ProductDTO addProduct(ProductEntity product) {
        return ProductDTO.toProductDto(productRepository.save(product));
    }

    public ResponseEntity<ProductDTO> updateProduct(ProductEntity changedProduct, Long productId) {
        try {
            ProductEntity product = productRepository.findById(productId).orElseThrow();
            if(changedProduct.getName() != null) {
                product.setName(changedProduct.getName());
            }
            if(changedProduct.getDescription() != null) {
                product.setDescription(changedProduct.getDescription());
            }
            if(changedProduct.getPrice() != 0) {
                product.setPrice(changedProduct.getPrice());
            }
            return ResponseEntity.ok().body(ProductDTO.toProductDto(product));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    public ResponseEntity<ProductDTO> removeProduct(Long productId) {
        try {
            ProductEntity product = productRepository.findById(productId).orElseThrow();
            List<CartEntity> productCarts = product.getCarts();

            productCarts.forEach(cartEntity -> {
                cartEntity.getProducts().remove(product);
                cartEntity.setAmount(cartEntity.getAmount() - product.getPrice());
                cartEntity.setCountOfProducts(cartEntity.getCountOfProducts() - 1);
            });
            productRepository.delete(product);

            return ResponseEntity.ok().body(ProductDTO.toProductDto(product));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
