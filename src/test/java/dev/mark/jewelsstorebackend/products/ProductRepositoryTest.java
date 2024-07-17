package dev.mark.jewelsstorebackend.products;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import dev.mark.jewelsstorebackend.categories.Category;

 /*
 * @AutoConfigureTestDatabase(replace=Replace.NONE)
 */

@DataJpaTest
public class ProductRepositoryTest {
    
    @Autowired
    TestEntityManager entityManager;

    @Autowired
    ProductRepository repository;

    Product newProduct = new Product();

    @Test
    @DisplayName("Repository test: Should find all products")
    void testShouldGetAllProducts() {
        List<Product> products = repository.findAll();
        assertThat(products, hasSize(greaterThan(1)));
        assertThat(products.get(0).getProductName()).isEqualTo("Flamenco Abanico Earrings");
    }

    @Test
    @DisplayName("Repository test: Should find product by id")
    void testShouldGetOneProductById() {
        Product product = repository.findById(2L).orElseThrow();
        assertEquals(2L, product.getId());
    }

    @Test
    @DisplayName("Repository test: Should find product by name")
    void testShouldGetAProductByName() {
        
        newProduct.setProductName("Something");
        entityManager.persist(newProduct);

        Product searchedProduct = repository.findByProductName("Something").orElseThrow(() -> new ProductNotFoundException("Product not found"));
        
        assertEquals("Something", searchedProduct.getProductName());
    }

    @Test
    @DisplayName("Repository test: Should delete product by ID")
    void testDeleteProductById() {

        repository.deleteById(1L);

        Optional<Product> deletedProduct = repository.findById(1L);
        assertFalse(deletedProduct.isPresent());
    }

    @Test
    @DisplayName("Repository test: Find all by product name containing a string and ignore case")
    void testShouldGetProductsByNameContainingAndIgnoreCase() {

        newProduct.setProductName("SDRGFZX");
        entityManager.persist(newProduct);

        List<Product> foundProducts = repository.findByProductNameContainingIgnoreCase("DrgFz").orElseThrow();
        assertThat(foundProducts, hasSize(equalTo(1)));
        assertThat(foundProducts.get(0).getProductName()).isEqualTo("SDRGFZX");
    }

    @Test
    @DisplayName("Repository test: Find all by category name and ignore case")
    void testShouldGetProductByNameAndIgnoreCase() {

        Category newCategory = new Category();
        newCategory.setCategoryName("SDRGFZX");

        entityManager.persist(newCategory);

        Set<Category> categories = new HashSet<Category>();
        categories.add(newCategory);

        newProduct.setCategories(categories);

        entityManager.persist(newProduct);

        List<Product> foundProducts = repository.findProductsByCategoryNameIgnoreCase("SdRGFZx").orElseThrow();
        assertThat(foundProducts, hasSize(equalTo(1)));
        assertThat(foundProducts.get(0).getCategories()).contains(newCategory);
    }

    @AfterEach
    void tearDown() {
        entityManager.clear();
    }

}