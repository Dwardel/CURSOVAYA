package com.shop.repository;

import com.shop.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    @Override
    Optional<ProductEntity> findById(Long id);
}
