package dev.mark.jewelsstorebackend.images;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.MessageFormat;

import javax.management.RuntimeErrorException;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import dev.mark.jewelsstorebackend.config.StorageProperties;
import dev.mark.jewelsstorebackend.interfaces.IStorageService;
import dev.mark.jewelsstorebackend.products.Product;
import dev.mark.jewelsstorebackend.products.ProductNotFoundException;
import dev.mark.jewelsstorebackend.products.ProductRepository;
import dev.mark.jewelsstorebackend.utilities.Time;

@Service
public class ImageService implements IStorageService {

    ImageRepository imageRepository;
    ProductRepository productRepository;
    Time time;
    private final Path rootLocation;
    private final String uploadDir = "src/main/resources/static/images/";

    public ImageService(ImageRepository imageRepository, ProductRepository productRepository, Time time, StorageProperties properties) {
        if(properties.getLocation().trim().length() == 0){
            throw new StorageException("File upload location can not be Empty."); 
        }

		this.rootLocation = Paths.get(properties.getLocation());
        this.imageRepository = imageRepository;
        this.productRepository = productRepository;
        this.time = time;
    }

    @Override
    public String save(@NonNull Long productId, MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String baseName = fileName.substring(0, fileName.lastIndexOf("."));
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
        String combinedName = MessageFormat.format("{0}-{1}.{2}", baseName, time.checkCurrentTime(), fileExtension);
        Path path = Paths.get(uploadDir, combinedName);

        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product not found"));

        Image newImage = Image.builder()
            .imageName(combinedName)
            .product(product)
            .build();

        try (InputStream inputStream = file.getInputStream()) {
            if (file.isEmpty()) {
				throw new StorageException("Failed to store empty file.");
			}
            if (combinedName.contains("MainImage")) {
                newImage.setMainImage(true);
            }
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
            imageRepository.save(newImage);
        } catch (IOException e) {
            throw new RuntimeErrorException(null, "File" + combinedName + "has not been saved");
        }

        return combinedName;
    }

    @Override
	public Path load(String filename) {
		return rootLocation.resolve(filename);
	}

    @Override
	public Resource loadAsResource(String filename) {
		try {
			Path file = load(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			}
			else {
				throw new StorageFileNotFoundException(
						"Could not read file: " + filename);

			}
		}
		catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("Could not read file: " + filename, e);
		}
	}

    public Image markImageAsMain(Image image) {
        image.setMainImage(true);
        return image;
    }
}