package dev.mark.jewelsstorebackend.products;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
    public Optional<Product> findByProductName(String name);
}