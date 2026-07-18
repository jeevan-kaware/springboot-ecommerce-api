package com.jeevan.ecommerce.repository;

import com.jeevan.ecommerce.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository
        extends JpaRepository<Product, Long> {

    List<Product> findByCategoryIdAndActiveTrue(Long categoryId);

    List<Product> findByNameContainingIgnoreCaseAndActiveTrue(String keyword);

    Page<Product> findByActiveTrue(Pageable pageable);

    List<Product> findAllByActiveTrue();

    Optional<Product> findByIdAndActiveTrue(Long id);
}