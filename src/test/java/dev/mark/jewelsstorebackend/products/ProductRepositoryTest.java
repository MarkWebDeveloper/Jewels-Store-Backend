package dev.mark.jewelsstorebackend.products;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
    void testShouldGetAllProducts() {
        newProduct.setProductName("Something");
        entityManager.persist(newProduct);

        Product newProduct2 = new Product();
        newProduct2.setProductName("Something Else");
        entityManager.persist(newProduct2);

        List<Product> products = repository.findAll();

        assertThat(products, hasSize(greaterThan(1)));
        assertThat(products.contains(newProduct2));
    }

    @Test
    void testShouldGetPaginatedProducts() {
        newProduct.setProductName("Something");
        entityManager.persist(newProduct);
        
        Product newProduct2 = new Product();
        newProduct2.setProductName("Something Else");
        entityManager.persist(newProduct2);

        long productsCount = repository.count();
        int lastPageNumber = (int) (productsCount / 2 - 1);
        Pageable pageable = PageRequest.of(lastPageNumber, 2);
        Page<Product> productsPage = repository.findAll(pageable);
        List<Product> products = productsPage.getContent();

        assertThat(products.size(), is(2));
        assertTrue(products.contains(newProduct2));
    }

    @Test
    void testShouldGetOneProductById() {
        Product product = repository.findById(2L).orElseThrow();
        assertEquals(2L, product.getId());
    }

    @Test
    void testShouldGetAProductByName() {
        
        newProduct.setProductName("Something");
        entityManager.persist(newProduct);

        Product searchedProduct = repository.findByProductName("Something").orElseThrow(() -> new ProductNotFoundException("Product not found"));
        
        assertEquals("Something", searchedProduct.getProductName());
    }

    @Test
    void testDeleteProductById() {

        repository.deleteById(1L);

        Optional<Product> deletedProduct = repository.findById(1L);
        assertFalse(deletedProduct.isPresent());
    }

    @Test
    void testShouldGetProductsByNameContainingAndIgnoreCase() {

        newProduct.setProductName("SDRGFZX");
        entityManager.persist(newProduct);

        List<Product> foundProducts = repository.findByProductNameContainingIgnoreCase("DrgFz").orElseThrow();
        assertThat(foundProducts, hasSize(equalTo(1)));
        assertThat(foundProducts.get(0).getProductName()).isEqualTo("SDRGFZX");
    }

    @Test
    void testShouldGetProductByNameAndIgnoreCase() {

        Category newCategory = new Category();
        newCategory.setCategoryName("SDRGFZX");

        entityManager.persist(newCategory);

        Set<Category> categories = new HashSet<Category>();
        categories.add(newCategory);

        newProduct.setCategories(categories);

        entityManager.persist(newProduct);

        Pageable pageable = PageRequest.of(0, 2);

        Page<Product> foundProductsPage = repository.findProductsByCategoryNameIgnoreCase("SdRGFZx", pageable).orElseThrow();
        List<Product> foundProducts = foundProductsPage.getContent();
        assertThat(foundProducts, hasSize(equalTo(1)));
        assertThat(foundProducts.get(0).getCategories()).contains(newCategory);
    }

    @Test
    void testShouldSaveAndReturnNewProduct() {

        newProduct.setProductName("Test name");
        newProduct.setPrice(3000L);
        Product savedProduct = repository.save(newProduct);

        Product searchedProduct = repository.findByProductName("Test name").orElseThrow(() -> new ProductNotFoundException("Product not found"));

        assertEquals(savedProduct.getProductName(), newProduct.getProductName());
        assertEquals(savedProduct.getPrice(), newProduct.getPrice());
        assertEquals(savedProduct.getProductName(), searchedProduct.getProductName());
    }

    @Test
    void testShouldDeleteAnExistingProduct() {

        newProduct.setProductName("Test name");
        newProduct.setPrice(3000L);
        entityManager.persist(newProduct);

        repository.delete(newProduct);

        Optional<Product> deletedProduct = repository.findByProductName("Test name");
        
        assertFalse(deletedProduct.isPresent());
    }

    @AfterEach
    void tearDown() {
        entityManager.clear();
    }

}