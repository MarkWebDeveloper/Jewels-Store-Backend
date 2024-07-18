package dev.mark.jewelsstorebackend.products;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mysql.cj.protocol.ExportControlled;

import dev.mark.jewelsstorebackend.categories.Category;
import dev.mark.jewelsstorebackend.categories.CategoryRepository;
import dev.mark.jewelsstorebackend.products.facades.ProductFacade;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    ProductService service;

    @Mock
    ProductRepository repository;

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    ProductFacade productFacade;

    Product earring;

    Product necklace;

    {
        service = new ProductService(repository, categoryRepository, productFacade);

        earring = Product.builder().productName("Earring").id(1L).build();

        necklace = Product.builder().productName("Necklace").id(2L).build();
    }

    @Test
    void testGetAllProducts() {

        List<Product> products = new ArrayList<>();
        products.add(earring);
        products.add(necklace);

        when(repository.findAll()).thenReturn(products);
        List<Product> result = service.getAll();

        assertThat(result, contains(earring, necklace));

    }

    @Test
    void testShouldReturnProductById() throws Exception {
        
        when(repository.findById(1L)).thenReturn(Optional.of(earring));
        Product product = service.getById(1L);

        assertThat(product, is(earring));
    }

    @Test
    void testShouldReturnProductByName() throws Exception {
        
        when(repository.findByProductName("Earring")).thenReturn(Optional.of(earring));
        Product product = service.getByName("Earring");

        assertThat(product, is(earring));
    }

    @Test
    void testShouldReturnProductNotFound() {
        
        when(repository.findById(1L)).thenThrow(new ProductException("Product not found"));
        ProductException exception = assertThrows(ProductException.class, () -> service.getById(1L));

        String actualMessage = exception.getMessage();
        String expectedMessage = "Product not found";

        assertThat(actualMessage, is(expectedMessage));
        assertThat(actualMessage.contains(expectedMessage), is(true));

    }

    @Test
    void testShouldGetManyByCategoryName() throws Exception {
        Product earring2 = Product.builder().productName("Earring2").id(3L).build();
        Category earringsCategory = new Category();
        earringsCategory.setCategoryName("Earrings");
        Set<Category> categories = new HashSet<>();
        categories.add(earringsCategory);

        earring.setCategories(categories);
        earring2.setCategories(categories);

        List<Product> products = new ArrayList<>();
        products.add(earring);
        products.add(earring2);

        when(repository.findProductsByCategoryNameIgnoreCase("earRings")).thenReturn(Optional.of(products));

        List<Product> result = service.getManyByCategoryName("earRings");

        assertThat(result, contains(earring, earring2));
    }

}