package dev.mark.jewelsstorebackend.interfaces;

import java.util.List;

import org.springframework.lang.NonNull;
import dev.mark.jewelsstorebackend.messages.Message;

public interface IGenericProductService<T, DTO> extends IGenericGetService<T>{
    List<T> getAll();
    List<T> getAll(Integer page, Integer size);
    Long countAll();
    T getByName(String name) throws Exception;
    List<T> getManyByName(String name) throws Exception;
    List<T> getManyByCategoryName(String name, Integer page, Integer size) throws Exception;
    public T save(DTO obj) throws Exception;
    public T update(@NonNull Long id, DTO obj) throws Exception;
    public Message delete (@NonNull Long id) throws Exception;
}
