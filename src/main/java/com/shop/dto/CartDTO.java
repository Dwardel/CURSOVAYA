package com.shop.dto;

import com.shop.entity.CartEntity;
import lombok.Data;

import java.util.List;

@Data
public class CartDTO {
    private Long id;
    private Long cntProd;
    private Long amount;
    private List<ProductDTO> products;

    public CartDTO(Long id, Long cntProd, Long amount, List<ProductDTO> products) {
        this.id = id;
        this.cntProd = cntProd;
        this.amount = amount;
        this.products = products;
    }

    public static CartDTO toCartDto(CartEntity basket) {
        return new CartDTO(basket.getId(), basket.getCountOfProducts(),
                basket.getAmount(), basket.getProducts().stream().map(ProductDTO::toProductDto).toList());
    }
}
