package dev.mark.jewelsstorebackend.categories;

import java.util.List;

import org.springframework.lang.NonNull;

import dev.mark.jewelsstorebackend.messages.Message;

public interface IGenericCategoryService<T, DTO> {
    List<T> getAll();
    T getById(@NonNull Long id) throws Exception;
    T getByName(String name) throws Exception;
    public T save(DTO obj) throws Exception;
    public T update(@NonNull Long id, DTO obj) throws Exception;
    public Message delete (@NonNull Long id) throws Exception;
}
