package dev.mark.jewelsstorebackend.products;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
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

    @Test
    @DisplayName("Find all products")
    void testGetAllProducts() {
        List<Product> products = repository.findAll();
        assertEquals(15, products.size());
        assertThat(products.get(0).getProductName()).isEqualTo("Tope de puerta flexible");
    }

    @Test
    @DisplayName("Find product by id")
    void testGetOneProductById() {
        Product product = repository.findById(2L).orElseThrow();
        assertEquals(2L, product.getId());
        assertEquals("Papelera de sobremesa", product.getProductName());
    }

    @Test
    @DisplayName("Find product by name")
    void testGetProductsByName() {
        Product newProduct = new Product();
        newProduct.setProductName("Una cosa inutil");
        entityManager.persist(newProduct);

        Product searchedProduct = repository.findByProductName("Una cosa inutil").orElseThrow(() -> new ProductNotFoundException("Product not found"));
        
        assertEquals("Una cosa inutil", searchedProduct.getProductName());
    }

    @Test
    @DisplayName("Test delete product")
    void testDeleteProductById() {
        Product newProduct = new Product();
        entityManager.persist(newProduct);

        repository.deleteById(16L);

        List<Product> products = repository.findAll();
        assertThat(products.size(), is(15));
        assertThat(products.get(0).getProductName(), containsString("Tope de puerta flexible"));
    }

    @AfterEach
    void tearDown() {
        entityManager.clear();
    }

}