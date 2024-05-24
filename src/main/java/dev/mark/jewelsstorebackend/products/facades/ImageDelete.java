package dev.mark.jewelsstorebackend.products.facades;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import dev.mark.jewelsstorebackend.config.StorageProperties;
import dev.mark.jewelsstorebackend.images.Image;
import dev.mark.jewelsstorebackend.images.ImageRepository;
import dev.mark.jewelsstorebackend.interfaces.IDelete;
import dev.mark.jewelsstorebackend.products.Product;
import dev.mark.jewelsstorebackend.products.ProductNotFoundException;
import dev.mark.jewelsstorebackend.products.ProductRepository;

@Component
public class ImageDelete implements IDelete {

    ProductRepository productRepository;
    ImageRepository imageRepository;
    private final Path rootLocation;

    public ImageDelete(ProductRepository productRepository, ImageRepository imageRepository, StorageProperties properties) {
        this.productRepository = productRepository;
        this.imageRepository = imageRepository;
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public String delete(Long id) {

        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));

        String filename = new String();
        List<String> filenames = new ArrayList<>();

        if (product.getImages() != null) {
            for (Image image : product.getImages()) {
                try {
                    filename = image.getImageName();
                    filenames.add(filename);
                    Path file = rootLocation.resolve(image.getImageName());
                    Files.deleteIfExists(file);
                } catch (IOException e) {
                    throw new RuntimeException("Error: " + e.getMessage());
                }
            }
        }

        return "Images with names '" + filenames +  "' are deleted successfully";
    }
}