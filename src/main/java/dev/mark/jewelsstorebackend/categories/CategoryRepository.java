package dev.mark.jewelsstorebackend.categories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    public Optional<Category> findByCategoryName(String name);
}