package dev.mark.jewelsstorebackend.products;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import dev.mark.jewelsstorebackend.categories.Category;
import dev.mark.jewelsstorebackend.categories.CategoryNotFoundException;
import dev.mark.jewelsstorebackend.categories.CategoryRepository;
import dev.mark.jewelsstorebackend.images.Image;
import dev.mark.jewelsstorebackend.images.ImageRepository;
import dev.mark.jewelsstorebackend.interfaces.IGenericFullService;
import dev.mark.jewelsstorebackend.messages.Message;

@Service
public class ProductService implements IGenericFullService<Product, ProductDTO> {

    ProductRepository repository;
    CategoryRepository categoryRepository;
    ImageRepository imageRepository;

    public ProductService(ProductRepository repository, CategoryRepository categoryRepository, ImageRepository imageRepository) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
        this.imageRepository = imageRepository;
    }

    @Override
    public List<Product> getAll() {
        List<Product> countries = repository.findAll();
        return countries;
    }

    @Override
    public Product getById(@NonNull Long id) throws Exception {
        Product product = repository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));

        return product;
    }

    @Override
    public Product getByName(String name) throws Exception {
        Product product = repository.findByProductName(name).orElseThrow(() -> new ProductNotFoundException("Product not found"));

        return product;
    }

    @Override
    public Product save(ProductDTO product) {

        Category category = categoryRepository.findById(product.categoryId).orElseThrow(() -> new CategoryNotFoundException("Category not found"));

        Image image = imageRepository.findByImageName("placeholder-image.jpg").orElseThrow(() -> new StorageFileNotFoundException("Image not found"));

        Set<Image> images = new HashSet<Image>();

        images.add(image);
        
        Product newProduct = Product.builder()
            .productName(product.productName)
            .productDescription(product.productDescription)
            .price(product.price)
            .images(images)
            .build();

        Set<Category> categories = new HashSet<>();
        categories.add(category);

        newProduct.setCategories(categories);

        repository.save(newProduct);

        return newProduct;
    }

    @Override
    public Product update(@NonNull Long id, ProductDTO product) throws Exception {
        
        Product updatingProduct = repository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));

        Category category = categoryRepository.findById(product.categoryId).orElseThrow(() -> new CategoryNotFoundException("Category not found"));

        updatingProduct.setProductName(product.productName);
        updatingProduct.setProductDescription(product.productDescription);
        updatingProduct.setPrice(product.price);

        Set<Category> categories = new HashSet<>();
        categories.add(category);

        updatingProduct.setCategories(categories);

        repository.save(updatingProduct);
        
        return updatingProduct;
    }

    @Override
    public Message delete(@NonNull Long id) throws Exception {
        
        Product product = repository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));

        String productName = product.getProductName();

        repository.delete(product);

        Message message = new Message();

        message.createMessage("Product with the name '" + productName + "' is deleted from the products table");

        return message;
    }
    
}