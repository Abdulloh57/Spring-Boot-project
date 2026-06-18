package library.uz.springbootwithjpa.controller;

import jakarta.validation.Valid;
import library.uz.springbootwithjpa.dto.request.ProductCreateDto;
import library.uz.springbootwithjpa.dto.response.ProductResponseDto;
import library.uz.springbootwithjpa.service.FileServise;
import library.uz.springbootwithjpa.service.ProductServise;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("admin/products")
@RequiredArgsConstructor
public class AdminProductController {
    private final ProductServise productServise;
    private final FileServise fileServise;

    /// Buni chatGpt aytishi bo'yicha pathVariable bilan yozish dizayn jihatidan yaxshi emas ekan
    /// chunki products/1 degani odatda 1 id product parametrlari kerak degan maqsadda ishlatilar ekan
    /// keyin odatda filter bilan qilinar va dto qaytarilar ekan katta proyektlarda
    @GetMapping
    public List<ProductResponseDto> getProducts(@RequestParam Integer categoryId){
       return productServise.getProducts(categoryId);
    }

    @PostMapping
    public ProductResponseDto create(@Valid @ModelAttribute  ProductCreateDto req, @RequestParam("file") MultipartFile file ) throws IOException {
        String upload = fileServise.upload(file);
        return productServise.addProduct(req,upload);
    }
}
