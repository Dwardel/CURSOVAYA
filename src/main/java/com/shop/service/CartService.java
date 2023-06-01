package com.shop.service;

import com.shop.dto.CartDTO;
import com.shop.entity.CartEntity;
import com.shop.entity.ProductEntity;
import com.shop.repository.CartRepository;
import com.shop.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public List<CartDTO> carts() {
        return cartRepository.findAll().stream().map(CartDTO::toCartDto).toList();
    }

    public ResponseEntity<CartDTO> addProductToCartByCartId(Long cartId, Long productId) {
        try {
            CartEntity cart = cartRepository.findById(cartId).orElseThrow();
            ProductEntity product = productRepository.findById(productId).orElseThrow();

            cart.setCountOfProducts(cart.getCountOfProducts() + 1);
            cart.setAmount(cart.getAmount() + (product.getPrice()));

            List<CartEntity> carts = product.getCarts();
            carts.add(cart);
            product.setCarts(carts);

            return ResponseEntity.ok().body(CartDTO.toCartDto(cartRepository.save(cart)));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    public ResponseEntity<CartDTO> deleteCartProductByProductId(Long cartId, Long productId) {
        try {
            CartEntity cart = cartRepository.findById(cartId).orElseThrow();
            ProductEntity product = productRepository.findById(productId).orElseThrow();

            cart.setCountOfProducts(cart.getCountOfProducts() - 1);
            cart.setAmount(cart.getAmount() - product.getPrice());

            List<CartEntity> productCarts = product.getCarts();
            List<ProductEntity> cartProducts = cart.getProducts();

            productCarts.remove(cart);

            cartProducts.remove(product);
            cart.setProducts(cartProducts);

            return ResponseEntity.ok().body(CartDTO.toCartDto(cartRepository.save(cart)));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}

