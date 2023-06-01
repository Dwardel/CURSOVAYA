package com.shop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customer", uniqueConstraints = {
        @UniqueConstraint(columnNames = "phNum")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fio;
    private String phNum;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private CartEntity cart; //корзина

    public CustomerEntity(String fio, String phNum) {
        this.fio = fio;
        this.phNum = phNum;
    }
}
