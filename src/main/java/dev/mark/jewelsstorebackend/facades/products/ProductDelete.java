package dev.mark.jewelsstorebackend.facades.products;

import org.springframework.stereotype.Component;

import dev.mark.jewelsstorebackend.interfaces.IDelete;
import dev.mark.jewelsstorebackend.products.Product;
import dev.mark.jewelsstorebackend.products.ProductNotFoundException;
import dev.mark.jewelsstorebackend.products.ProductRepository;

@Component
public class ProductDelete implements IDelete {

    ProductRepository productRepository;

    public ProductDelete(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public String delete(Long id) {

        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));

        String productName = product.getProductName();

        productRepository.delete(product);

        return "Product '" + productName +  "' is deleted successfully.";
    }
}