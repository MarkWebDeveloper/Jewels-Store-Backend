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

import dev.mark.jewelsstorebackend.categories.Category;
import dev.mark.jewelsstorebackend.categories.CategoryRepository;
import dev.mark.jewelsstorebackend.products.facades.ProductFacade;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    ProductService productService;

    @Mock
    ProductRepository productRepository;

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    ProductFacade productFacade;

    Product earring;

    Product necklace;

    Set<Category> earringCategories;

    {
        productService = new ProductService(productRepository, categoryRepository, productFacade);
        earring = Product.builder().productName("Earring").id(1L).build();
        necklace = Product.builder().productName("Necklace").id(2L).build();

        Category earringsCategory = Category.builder().categoryName("Earrings").build();
        earringCategories = new HashSet<>();
        earringCategories.add(earringsCategory);
    }

    @Test
    void testGetAllProducts() {

        List<Product> products = new ArrayList<>();
        products.add(earring);
        products.add(necklace);

        when(productRepository.findAll()).thenReturn(products);
        List<Product> result = productService.getAll();

        assertThat(result, contains(earring, necklace));

    }

    @Test
    void testShouldReturnProductById() throws Exception {
        
        when(productRepository.findById(1L)).thenReturn(Optional.of(earring));
        Product product = productService.getById(1L);

        assertThat(product, is(earring));
    }

    @Test
    void testShouldReturnProductByName() throws Exception {
        
        when(productRepository.findByProductName("Earring")).thenReturn(Optional.of(earring));
        Product product = productService.getByName("Earring");

        assertThat(product, is(earring));
    }

    @Test
    void testShouldReturnProductNotFound() {
        
        when(productRepository.findById(1L)).thenThrow(new ProductException("Product not found"));
        ProductException exception = assertThrows(ProductException.class, () -> productService.getById(1L));

        String actualMessage = exception.getMessage();
        String expectedMessage = "Product not found";

        assertThat(actualMessage, is(expectedMessage));
        assertThat(actualMessage.contains(expectedMessage), is(true));
    }

    @Test
    void testShouldGetManyByCategoryName() throws Exception {

        Product earring2 = Product.builder().productName("Earring2").id(3L).build();

        earring.setCategories(earringCategories);
        earring2.setCategories(earringCategories);

        List<Product> products = new ArrayList<>();
        products.add(earring);
        products.add(earring2);

        when(productRepository.findProductsByCategoryNameIgnoreCase("earRings")).thenReturn(Optional.of(products));

        List<Product> result = productService.getManyByCategoryName("earRings");

        assertThat(result, contains(earring, earring2));
    }

}