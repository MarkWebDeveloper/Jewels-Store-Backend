package dev.mark.jewelsstorebackend.interfaces;

import org.springframework.lang.NonNull;

public interface IGenericGetService<T> {
    
    T getById(@NonNull Long id) throws Exception;
}