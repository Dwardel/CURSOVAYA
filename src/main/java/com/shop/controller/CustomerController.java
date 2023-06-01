package com.shop.controller;

import com.shop.dto.CustomerDTO;
import com.shop.dto.ProductDTO;
import com.shop.entity.CustomerEntity;
import com.shop.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/")
    public ResponseEntity<List<CustomerDTO>> customers() {
        return ResponseEntity.ok().body(customerService.customers());
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> customer(@PathVariable Long customerId) {
        return customerService.customer(customerId);
    }

    @GetMapping("/{customerId}/cart/products")
    public ResponseEntity<List<ProductDTO>> getCartProductsByCustomerId(@PathVariable Long customerId) {
        return customerService.getCartProductsByCustomerId(customerId);
    }

    @PostMapping("/")
    public ResponseEntity<CustomerDTO> addCustomer(@RequestBody CustomerEntity customer) {
        return ResponseEntity.ok().body(customerService.addCustomer(customer));
    }

    @PatchMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> updateCustomer(@RequestBody CustomerEntity customer, @PathVariable Long customerId) {
        return customerService.updateCustomer(customer, customerId);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> removeCustomer(@PathVariable Long customerId) {
        return customerService.removeCustomer(customerId);
    }
}