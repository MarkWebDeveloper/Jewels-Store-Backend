package dev.mark.jewelsstorebackend.products;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

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
    @DisplayName("Find all products")
    void testShouldGetAllProducts() {
        List<Product> products = repository.findAll();
        assertThat(products, hasSize(greaterThan(1)));
        assertThat(products.get(0).getProductName()).isEqualTo("Flamenco Abanico Earrings");
    }

    @Test
    @DisplayName("Find product by id")
    void testShouldGetOneProductById() {
        Product product = repository.findById(2L).orElseThrow();
        assertEquals(2L, product.getId());
    }

    @Test
    @DisplayName("Find product by name")
    void testShouldGetAProductByName() {
        
        newProduct.setProductName("Something");
        entityManager.persist(newProduct);

        Product searchedProduct = repository.findByProductName("Something").orElseThrow(() -> new ProductNotFoundException("Product not found"));
        
        assertEquals("Something", searchedProduct.getProductName());
    }

    @Test
    @DisplayName("Test delete product")
    void testDeleteProductById() {
        
        entityManager.persist(newProduct);

        repository.deleteById(4L);

        List<Product> products = repository.findAll();
        assertThat(products.size(), is(3));
        assertThat(products.get(0).getProductName(), containsString("Flamenco Abanico Earrings"));
    }

    @AfterEach
    void tearDown() {
        entityManager.clear();
    }

}