package dev.mark.jewelsstorebackend.categories;

import java.util.List;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.mark.jewelsstorebackend.interfaces.IGenericFullService;
import dev.mark.jewelsstorebackend.messages.Message;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping(path = "${api-endpoint}")
public class CategoryController {

    IGenericFullService<Category, CategoryDTO> service;

    @GetMapping(path = "/all/categories")
    public List<Category> index() {

        List<Category> categories = service.getAll();

        return categories;
    }

    @GetMapping(path = "/all/categories/{id}")
    public ResponseEntity<Category> findById(@PathVariable("id") @NonNull Long id) throws Exception {

        Category category = service.getById(id);

        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(category);
    }

    @GetMapping(path = "/all/categories/getByName/{name}")
    public ResponseEntity<Category> findById(@PathVariable("name") @NonNull String name) throws Exception {

        Category category = service.getByName(name);

        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(category);
    }

    @PostMapping(path = "/admin/categories")
    public ResponseEntity<Category> create(@RequestBody CategoryDTO category) throws Exception {

        Category newCategory = service.save(category);

        return ResponseEntity.status(201).body(newCategory);
    }

    @PutMapping("/admin/categories/{id}")
    public ResponseEntity<Category> update(@PathVariable("id") @NonNull Long id, @RequestBody CategoryDTO category) throws Exception {

        Category updatedCategory = service.update(id, category);

        return ResponseEntity.status(200).body(updatedCategory);
    }

    @DeleteMapping(path = "/admin/categories/{id}")
    public ResponseEntity<Message> remove(@PathVariable("id") @NonNull Long id) throws Exception { 

        Message delete = service.delete(id);

        return ResponseEntity.status(200).body(delete);
    }

}