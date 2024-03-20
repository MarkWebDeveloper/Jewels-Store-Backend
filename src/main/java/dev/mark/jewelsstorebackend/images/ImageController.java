package dev.mark.jewelsstorebackend.images;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import dev.mark.jewelsstorebackend.interfaces.IStorageService;
import dev.mark.jewelsstorebackend.messages.ResponseMessage;

@RestController
@RequestMapping(path = "${api-endpoint}")
public class ImageController {

    @Autowired
    IStorageService service;

    @PostMapping(path = "/images/uploadImages/{id}")
    ResponseEntity<ResponseMessage> uploadImages(@PathVariable("id") @NonNull Long id, @RequestParam("file") MultipartFile file, @RequestParam("files") MultipartFile[] files) {

        String message = "";

        try {
            String mainFilename = new String();
            List<String> fileNames = new ArrayList<>();

            service.saveMainImage(id, file);
            mainFilename = file.getOriginalFilename();

            service.saveImages(id, files);
            Arrays.asList(files).stream().forEach(image -> {
                fileNames.add(image.getOriginalFilename());
            });

            message = "File with the name " + mainFilename + " and files " + fileNames + " are uploaded successfully: ";
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Fail to upload files!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @GetMapping("/images/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

		Resource file = service.loadAsResource(filename);

		if (file == null)
			return ResponseEntity.notFound().build();

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}
}