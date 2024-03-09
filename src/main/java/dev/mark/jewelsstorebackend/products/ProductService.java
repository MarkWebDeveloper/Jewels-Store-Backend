package dev.mark.jewelsstorebackend.products;

import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import dev.mark.jewelsstorebackend.interfaces.IGenericFullService;
import dev.mark.jewelsstorebackend.messages.Message;

@Service
public class ProductService implements IGenericFullService<Product, ProductDTO> {

    ProductRepository repository;
    
    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> getAll() {
        List<Product> countries = repository.findAll();
        return countries;
    }

    public Product getById(@NonNull Long id) throws Exception {
        Product product = repository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));

        return product;
    }

    @SuppressWarnings("null")
    public Product save(@NonNull ProductDTO product) {
        
        Product newProduct = Product.builder()
            .productName(product.productName)
            .description(product.description)
            .image(product.image)
            .price(product.price)
            .build();

        repository.save(newProduct);

        return newProduct;
    }

    public Product update(@NonNull Long id, ProductDTO product) throws Exception {
        
        Product updatingProduct = repository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        
        updatingProduct.setProductName(product.productName);
        updatingProduct.setDescription(product.description);
        updatingProduct.setImage(product.image);
        updatingProduct.setPrice(product.price);

        Product updatedProduct = repository.save(updatingProduct);
        
        return updatedProduct;
    }

    public Message delete(@NonNull Long id) throws Exception {
        
        Product product = repository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));

        String productName = product.getProductName();

        repository.delete(product);

        Message message = new Message();

        message.createMessage("Product with the name " + productName + " is deleted from the products table");

        return message;
    }
    
}