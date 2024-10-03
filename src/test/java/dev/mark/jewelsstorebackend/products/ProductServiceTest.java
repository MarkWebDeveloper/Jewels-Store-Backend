package dev.mark.jewelsstorebackend.products;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import dev.mark.jewelsstorebackend.categories.Category;
import dev.mark.jewelsstorebackend.categories.CategoryRepository;
import dev.mark.jewelsstorebackend.images.Image;
import dev.mark.jewelsstorebackend.messages.Message;
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

    Category earringsCategory;

    Set<Category> earringCategories;

    Category necklacesCategory;

    Set<Category> necklacesCategories;

    ProductDTO testProductDTO;

    {
        productService = new ProductService(productRepository, categoryRepository, productFacade);
        earring = Product.builder().productName("Earring").id(1L).build();
        necklace = Product.builder().productName("Necklace").id(2L).build();

        earringsCategory = Category.builder().categoryName("Earrings").id(1L).build();
        earringCategories = new HashSet<>();
        earringCategories.add(earringsCategory);

        necklacesCategory = Category.builder().categoryName("Necklaces").id(2L).build();
        necklacesCategories = new HashSet<>();
        necklacesCategories.add(necklacesCategory);

        testProductDTO = ProductDTO.builder()
            .productName("Test product")
            .productDescription("A test product")
            .price(2000L)
            .categoryId(1L)
            .build();
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
    void testGetPaginatedProducts() {

        List<Product> products = new ArrayList<>();
        products.add(earring);
        products.add(necklace);

        Page<Product> page1 = new PageImpl<>(products);

        PageRequest pageable1 = PageRequest.of(0, 2);

        when(productRepository.findAll(pageable1)).thenReturn(page1);
        List<Product> result = productService.getAll(2, 0);

        assertThat(result, is(page1.getContent()));
        assertTrue(result.contains(earring));
    }

    @Test
    void testCountAllProducts() {

        when(productRepository.count()).thenReturn(10L);
        Long productsCount = productService.countAll();

        assertThat(productsCount, is(10L));
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

    @Test
    void testShouldSaveNewProduct() throws Exception {

        when(categoryRepository.findById(testProductDTO.getCategoryId())).thenReturn(Optional.of(earringsCategory));
        Product testProduct = testProductDTO.toProduct(categoryRepository);
        when(productRepository.save(any(Product.class))).thenReturn(testProduct);

        Product savedProduct = productService.save(testProductDTO);

        productService.save(testProductDTO);

        assertNotNull(savedProduct);
        assertEquals(testProductDTO.getProductName(), savedProduct.getProductName());
        assertEquals(testProductDTO.getProductDescription(), savedProduct.getProductDescription());
        assertEquals(testProductDTO.getPrice(), savedProduct.getPrice());
        assertTrue(savedProduct.getCategories().contains(earringsCategory));
    }

    @Test
    void testShouldUpdateExistingProduct() throws Exception {

        Product originalProduct = necklace;
        var originalProductId = 1L;
        ProductDTO updatingProductDTO = testProductDTO;

        when(productRepository.findById(originalProductId)).thenReturn(Optional.of(originalProduct));
        when(categoryRepository.findById(updatingProductDTO.getCategoryId())).thenReturn(Optional.of(earringsCategory));
        Product transformedProduct = updatingProductDTO.toProduct(categoryRepository);
        when(productRepository.save(any(Product.class))).thenReturn(transformedProduct);

        Product updatedProduct = productService.update(originalProductId, updatingProductDTO);

        assertNotNull(updatedProduct);
        assertEquals(updatingProductDTO.getProductName(), updatedProduct.getProductName());
        assertEquals(updatingProductDTO.getProductDescription(), updatedProduct.getProductDescription());
        assertEquals(updatingProductDTO.getPrice(), updatedProduct.getPrice());
        assertTrue(updatedProduct.getCategories().contains(earringsCategory));
    }

    @Test
    void testShouldReturnMessageAboutDeletingProduct() throws Exception {

        Product product = earring;
        Image image = new Image();
        image.setImageName("test-product-image.jpg");
        Set<Image> images = new HashSet<>();
        images.add(image);
        product.setImages(images);

        when(productFacade.delete("image", product.getId())).thenReturn("Images with names [test-product-image.jpg] are deleted successfully");
        when(productFacade.delete("product", product.getId())).thenReturn("Product Earrings is deleted successfully.");

        Message resultingMessage = productService.delete(product.getId());
        Message expectedMessage = new Message();
        expectedMessage.createMessage("Product Earrings is deleted successfully. Images with names [test-product-image.jpg] are deleted successfully");

        assertThat(resultingMessage.getMessage(), is(expectedMessage.getMessage()));
    }

}