package dev.mark.jewelsstorebackend.interfaces;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;;

@Component
public interface IStorageService {

	String save(@NonNull Long id, MultipartFile file);

}