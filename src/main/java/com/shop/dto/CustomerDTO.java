package com.shop.dto;

import com.shop.entity.CustomerEntity;
import lombok.Data;

@Data
public class CustomerDTO {
    private Long id;
    private String fio;
    private String phNum;
    private CartDTO cartDTO;

    public CustomerDTO(Long id, String fio, String phNum, CartDTO cartDTO) {
        this.id = id;
        this.fio = fio;
        this.phNum = phNum;
        this.cartDTO = cartDTO;
    }

    public static CustomerDTO toCustomerDto(CustomerEntity customer) {
        return new CustomerDTO(customer.getId(), customer.getFio(), customer.getPhNum(), CartDTO.toCartDto(customer.getCart()));
    }
}
