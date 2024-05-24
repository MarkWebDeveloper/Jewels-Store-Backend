package dev.mark.jewelsstorebackend.interfaces;

public interface IGenericUpdateService<T, R> {

    R update(T type, Long id);
    
}
