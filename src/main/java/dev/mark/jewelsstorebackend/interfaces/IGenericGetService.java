package dev.mark.jewelsstorebackend.interfaces;

public interface IGenericGetService<T> {
    
    T getById(Long id) throws Exception;
}