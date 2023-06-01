package com.shop.service;

import com.shop.dto.CustomerDTO;
import com.shop.dto.ProductDTO;
import com.shop.entity.CartEntity;
import com.shop.entity.CustomerEntity;
import com.shop.entity.ProductEntity;
import com.shop.repository.CartRepository;
import com.shop.repository.CustomerRepository;
import com.shop.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CustomerService(CustomerRepository customerRepository, CartRepository cartRepository, ProductRepository productRepository) {
        this.customerRepository = customerRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public ResponseEntity<CustomerDTO> customer(Long id) {
        try {
            return ResponseEntity.ok().body(CustomerDTO.toCustomerDto(customerRepository.findById(id).orElseThrow()));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    public List<CustomerDTO> customers() {
        return customerRepository.findAll().stream().map(CustomerDTO::toCustomerDto).toList();
    }


    public ResponseEntity<List<ProductDTO>> getCartProductsByCustomerId(Long customerId) {
        try {
            return ResponseEntity.ok().body(customerRepository.findById(customerId).orElseThrow().getCart().getProducts().stream().map(ProductDTO::toProductDto).toList());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    public CustomerDTO addCustomer(CustomerEntity customer) {
        customer.setCart(new CartEntity(customer.getId(), 0L, 0L, customer));
        return CustomerDTO.toCustomerDto(customerRepository.save(customer));
    }

    public ResponseEntity<CustomerDTO> updateCustomer(CustomerEntity changedCustomer, Long id) {
        try {
            CustomerEntity customer = customerRepository.findById(id).orElseThrow();
            if(changedCustomer.getFio() != null) {
                customer.setFio(changedCustomer.getFio());
            }
            if(changedCustomer.getPhNum() != null) {
                customer.setPhNum(changedCustomer.getPhNum());
            }
            return ResponseEntity.ok().body(CustomerDTO.toCustomerDto(customer));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    public ResponseEntity<CustomerDTO> removeCustomer(Long id) {
        try {
            CustomerEntity customer = customerRepository.findById(id).orElseThrow();
            CartEntity cart = customer.getCart();

            List<ProductEntity> cartProducts = cart.getProducts();

            cartProducts.forEach(product -> product.getCarts().remove(cart));

            customerRepository.delete(customer);

            return ResponseEntity.ok().body(CustomerDTO.toCustomerDto(customer));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }
}
