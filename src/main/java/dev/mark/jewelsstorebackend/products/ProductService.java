package dev.mark.jewelsstorebackend.products;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import dev.mark.jewelsstorebackend.categories.Category;
import dev.mark.jewelsstorebackend.categories.CategoryNotFoundException;
import dev.mark.jewelsstorebackend.categories.CategoryRepository;
import dev.mark.jewelsstorebackend.interfaces.IGenericFullService;
import dev.mark.jewelsstorebackend.interfaces.IGenericSearchService;
import dev.mark.jewelsstorebackend.messages.Message;
import dev.mark.jewelsstorebackend.products.facades.ProductFacade;

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
    public Product save(@NonNull ProductDTO productDTO) throws Exception {
        
        Product newProduct = productDTO.toProduct(categoryRepository);

        repository.save(newProduct);

        return newProduct;
    }

    @Override
    public Product update(@NonNull Long id, ProductDTO product) throws Exception {
        
        Product updatingProduct = repository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));

        Category category = categoryRepository.findById(product.getCategoryId()).orElseThrow(() -> new CategoryNotFoundException("Category not found"));
        
        updatingProduct.setProductName(product.getProductName());
        updatingProduct.setProductDescription(product.getProductDescription());
        updatingProduct.setPrice(product.getPrice());

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