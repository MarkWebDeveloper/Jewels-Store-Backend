package dev.mark.jewelsstorebackend.products;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "${api-endpoint}")
public class ProductImageController {

    ProductImageStorageService service;

    @PostMapping(path = "/images")
    ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {

        service.storeFile(file);

        return ResponseEntity.status(201).body("Saved image");
    }
}