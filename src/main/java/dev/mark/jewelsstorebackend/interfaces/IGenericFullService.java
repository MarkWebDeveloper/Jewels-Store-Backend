package dev.mark.jewelsstorebackend.interfaces;

import java.util.List;

import dev.mark.jewelsstorebackend.messages.Message;

public interface IGenericFullService<T, DTO> {
    List<T> getAll();
    T getById(Long id);
    public T save(DTO obj);
    public T update(Long id, DTO obj) throws Exception;
    public Message delete (Long id) throws Exception;
}
