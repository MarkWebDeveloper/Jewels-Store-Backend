package dev.mark.jewelsstorebackend.interfaces;

import java.util.List;

import org.springframework.lang.NonNull;

import dev.mark.jewelsstorebackend.messages.Message;

public interface IGenericFullService<T, DTO> {
    List<T> getAll();
    T getById(@NonNull Long id) throws Exception;
    T getByName(String name) throws Exception;
    public T save(DTO obj);
    public T update(@NonNull Long id, DTO obj) throws Exception;
    public Message delete (@NonNull Long id) throws Exception;
}
