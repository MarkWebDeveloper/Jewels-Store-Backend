package dev.mark.jewelsstorebackend.products;

import java.util.HashSet;
import java.util.Set;

import dev.mark.jewelsstorebackend.categories.Category;
import dev.mark.jewelsstorebackend.categories.CategoryNotFoundException;
import dev.mark.jewelsstorebackend.categories.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private String productName;
    private String productDescription;
    private Long price;
    private Long categoryId;

    public Product toProduct(CategoryRepository categoryRepository) throws Exception {

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException("Category not found"));
        
        Product newProduct = Product.builder()
        .productName(productName)
        .productDescription(productDescription)
        .price(price)
        .build();
        
        Set<Category> categories = new HashSet<>();
        categories.add(category);
    
        newProduct.setCategories(categories);

        return newProduct;
    }
}
