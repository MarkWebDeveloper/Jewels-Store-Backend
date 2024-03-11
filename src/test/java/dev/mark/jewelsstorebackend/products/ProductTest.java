package dev.mark.jewelsstorebackend.products;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dev.mark.jewelsstorebackend.categories.Category;

public class ProductTest {

    Product product;
    Category category;

    @BeforeEach
    void setUp() {
        
        category = new Category();
        category.setCategoryName("Para hogar");

        Set<Category> categories = new HashSet<Category>();
        categories.add(category);
        
        category.setCategoryName("Para Hogar");
        product = new Product(1L, "Superman", "Una figura de Superman en 3D", "/superman.jpg", 12L, categories);
    }

    @Test
    void testProductHas6Attributes() {
        Field[] fields = product.getClass().getDeclaredFields();
        assertThat(fields.length, is(6));
    }

    @Test
    void testProductHaveIdProductnamePasswordProfile() {
        assertThat(product.getId(), is(1L));
        assertThat(product.getProductName(), is("Superman"));
        assertThat(product.getDescription(), is("Una figura de Superman en 3D"));
        assertThat(product.getProductImage(), is("/superman.jpg"));
        assertThat(product.getPrice(), is(12L));
    }
}