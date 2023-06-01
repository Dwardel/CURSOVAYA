package com.shop.controller;

import com.shop.dto.CartDTO;
import com.shop.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/")
    public ResponseEntity<List<CartDTO>> carts() {
        return ResponseEntity.ok().body(cartService.carts());
    }

    @PostMapping("/{cartId}/{productId}")
    public ResponseEntity<CartDTO> addProductToCartByCartId(@PathVariable Long cartId, @PathVariable Long productId) {
        return cartService.addProductToCartByCartId(cartId, productId);
    }

    @DeleteMapping("/{cartId}/{productId}")
    public ResponseEntity<CartDTO> deleteCartProductByProductId(@PathVariable Long cartId, @PathVariable Long productId) {
        return cartService.deleteCartProductByProductId(cartId, productId);
    }

}
