package dev.mark.jewelsstorebackend.profiles;

import org.springframework.lang.NonNull;

public interface IGenericProfileService<T> {
    public Profile getById(@NonNull Long id)throws Exception;
    public T getByEmail(@NonNull String email)throws Exception;
    public T update(ProfileDTO profileDTO, Long id);
    public String updateFavorites(Long productId) throws Exception;
}
