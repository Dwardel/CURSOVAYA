package com.shop.dto;

import com.shop.entity.CartEntity;
import com.shop.entity.ProductEntity;
import lombok.Data;

import java.util.List;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private int price;
    private List<Long> cartsId;

    public ProductDTO(Long id, String name, int price, List<Long> cartsId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.cartsId = cartsId;
    }

    public static ProductDTO toProductDto(ProductEntity product) {
        return new ProductDTO(product.getId(), product.getName(), product.getPrice() ,product.getCarts().stream().map(CartEntity::getId).toList());
    }
}
