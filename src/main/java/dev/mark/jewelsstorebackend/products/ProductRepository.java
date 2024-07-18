package dev.mark.jewelsstorebackend.products;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product,Long> {

    public Optional<Product> findByProductName(String name);
    public Optional<List<Product>> findByProductNameContainingIgnoreCase(String name);
    @Query("SELECT p FROM Product p JOIN p.categories c WHERE LOWER(c.categoryName) = LOWER(:categoryName)")
    public Optional <List<Product>> findProductsByCategoryNameIgnoreCase(@Param("categoryName") String categoryName);

}