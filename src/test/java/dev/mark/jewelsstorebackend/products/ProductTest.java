package dev.mark.jewelsstorebackend.products;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dev.mark.jewelsstorebackend.categories.Category;
import dev.mark.jewelsstorebackend.images.Image;

public class ProductTest {

    Product product;
    Category category;
    Image image;

    @BeforeEach
    void setUp() {
        
        category = new Category();
        category.setCategoryName("Para hogar");

        Set<Category> categories = new HashSet<Category>();
        categories.add(category);

        image = new Image();
        image.setImageName("superman-2.jpg");

        Set<Image> images = new HashSet<Image>();
        images.add(image);
        
        category.setCategoryName("Para Hogar");
        product = new Product(1L, "Superman", "Una figura de Superman en 3D", images, 12L, categories);
    }

    @Test
    void testProductHas6Attributes() {
        Field[] fields = product.getClass().getDeclaredFields();
        assertThat(fields.length, is(7));
    }

    @Test
    void testProductHasIdNameDescriptionMainImageImagesPriceCategories() {
        assertThat(product.getId(), is(1L));
        assertThat(product.getProductName(), is("Superman"));
        assertThat(product.getProductDescription(), is("Una figura de Superman en 3D"));
        assertThat(product.getPrice(), is(12.0F));
    }
}