package com.shop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cart")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long countOfProducts;
    private Long amount; //конечная сумма
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private CustomerEntity customer;

    @ManyToMany(cascade = {CascadeType.PERSIST}, mappedBy = "carts", fetch = FetchType.EAGER)
    private List<ProductEntity> products = new ArrayList<>();

    public CartEntity(Long id, Long countOfProducts, Long amount, CustomerEntity customer) {
        this.id = id;
        this.countOfProducts = countOfProducts;
        this.amount = amount;
        this.customer = customer;
    }
}
