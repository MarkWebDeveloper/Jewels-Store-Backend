package dev.mark.jewelsstorebackend.categories;

import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import dev.mark.jewelsstorebackend.interfaces.IGenericFullService;
import dev.mark.jewelsstorebackend.messages.Message;

@Service
public class CategoryService implements IGenericFullService<Category, CategoryDTO> {
    
    CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Category> getAll() {
        List<Category> countries = repository.findAll();
        return countries;
    }

    @Override
    public Category getById(@NonNull Long id) throws Exception {
        Category category = repository.findById(id).orElseThrow(() -> new CategoryNotFoundException("Category not found"));

        return category;
    }

    @Override
    public Category getByName(String name) throws Exception {
        Category category = repository.findByCategoryName(name).orElseThrow(() -> new CategoryNotFoundException("Category not found"));

        return category;
    }

    @Override
    public Category save(@NonNull CategoryDTO category) {
        
        Category newCategory = Category.builder()
            .categoryName(category.categoryName)
            .build();

        repository.save(newCategory);

        return newCategory;
    }

    @Override
    public Category update(@NonNull Long id, CategoryDTO category) throws Exception {
        
        Category updatingCategory = repository.findById(id).orElseThrow(() -> new CategoryNotFoundException("Category not found"));
        
        updatingCategory.setCategoryName(category.categoryName);

        repository.save(updatingCategory);
        
        return updatingCategory;
    }

    @Override
    public Message delete(@NonNull Long id) throws Exception {
        
        Category category = repository.findById(id).orElseThrow(() -> new CategoryNotFoundException("Category not found"));

        String categoryName = category.getCategoryName();

        repository.delete(category);

        Message message = new Message();

        message.createMessage("Category with the name '" + categoryName + "' is deleted from the categorys table");

        return message;
    }
}
