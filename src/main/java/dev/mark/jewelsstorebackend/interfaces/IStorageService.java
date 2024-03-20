package dev.mark.jewelsstorebackend.interfaces;

import java.nio.file.Path;

import org.springframework.core.io.Resource;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public interface IStorageService {

    void init();

    void saveMainImage(@NonNull Long id, MultipartFile file);

    void saveImages(@NonNull Long id, MultipartFile[] file);

	Path load(String filename);

	Resource loadAsResource(String filename);

    void deleteAll();

}