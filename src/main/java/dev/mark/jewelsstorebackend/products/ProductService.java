package dev.mark.jewelsstorebackend.products;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import dev.mark.jewelsstorebackend.categories.Category;
import dev.mark.jewelsstorebackend.categories.CategoryNotFoundException;
import dev.mark.jewelsstorebackend.categories.CategoryRepository;
import dev.mark.jewelsstorebackend.facades.products.ProductFacade;
import dev.mark.jewelsstorebackend.interfaces.IGenericFullService;
import dev.mark.jewelsstorebackend.interfaces.IGenericSearchService;
import dev.mark.jewelsstorebackend.messages.Message;

@Service
public class ProductService implements IGenericFullService<Product, ProductDTO>, IGenericSearchService<Product> {

    ProductRepository repository;
    CategoryRepository categoryRepository;
    ProductFacade productFacade;

    public ProductService(ProductRepository repository, CategoryRepository categoryRepository,
            ProductFacade productFacade) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
        this.productFacade = productFacade;
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
    public List<Product> getManyByName(String name) throws Exception {
        List<Product> product = repository.findByProductNameContainingIgnoreCase(name).orElseThrow(() -> new ProductNotFoundException("Product not found"));

        return product;
    }

    @Override
    public List<Product> getManyByCategoryName(String name) throws Exception {
        List<Product> product = repository.findProductsByCategoryNameIgnoreCase(name).orElseThrow(() -> new ProductNotFoundException("Product not found"));

        return product;
    }

    @Override
    public Product save(@NonNull ProductDTO product) {

        Category category = categoryRepository.findById(product.categoryId).orElseThrow(() -> new CategoryNotFoundException("Category not found"));
        
        Product newProduct = Product.builder()
            .productName(product.productName)
            .productDescription(product.productDescription)
            .price(product.price)
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

        String deleteImageResponse = productFacade.delete("image", id);
        String deleteProductResponse = productFacade.delete("product", id);

        Message message = new Message();

        message.createMessage(deleteProductResponse + " " + deleteImageResponse);

        return message;
    }
    
}