package com.project.shopapp.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.shopapp.dtos.ProductDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {
    @GetMapping("") // http://localhost:8088/api/v1/products
    public ResponseEntity<String> getAllProduct(
            @RequestParam("page") int productPage,
            @RequestParam("limit") int productLimit) {
        return ResponseEntity
                .ok("get Products here");
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getProductById(@PathVariable("id") String productId) {
        return ResponseEntity.ok("Product with id " + productId);
    }

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createProduct(
            @Valid @ModelAttribute ProductDTO productDTO,
            BindingResult result
            // @RequestParam("file") MultipartFile file
            ) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors().stream().map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages.toString());
            }
            MultipartFile file = productDTO.getFile();
            if (file != null) {
                // kiểm tra kích thước file và định dạng
                if (file.getSize() > 10 * 1024 * 1024) { // kích thước file > 10mb
                    // throw new ResponseStatusException(HttpStatus.PAYLOAD_TOO_LARGE, "File is too large (max = 10MB)");
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("File is too large (max = 10MB)");
                }
                String contentType = file.getContentType(); // lấy định dạng file
                if (contentType == null || !contentType.startsWith("image/")) {
                    // throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid file
                    // type");
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("Invalid file type");
                }
                // save file and update thumbnail in Object DTO
                String filename = storeFile(file); // thay thế hàm này với code để lưu file

            }

            return ResponseEntity.ok("Product created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private String storeFile(MultipartFile file) throws IOException {
        // lưu file vào thư mục
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        // thêm UUID vào trước tên file để đảm bảo tên file là duy nhất
        String uniqueFilename = UUID.randomUUID().toString() + "_" + filename;
        // đường dẫn muốn lưu file
        Path uploadDir = Paths.get("uploads");
        // Kiểm tra và tạo thư mục nếu nó không tồn tại
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        // đường dẫn đầy đủ đến file
        Path destination = Paths.get(uploadDir.toString(), uniqueFilename);
        // sao chép file đến thư mục đích
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFilename;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        return ResponseEntity.ok(String.format("Product with id = %d deleted successfully", id));
    }
}
