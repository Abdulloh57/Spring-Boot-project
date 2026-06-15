package library.uz.springbootwithjpa.controller;


import library.uz.springbootwithjpa.model.Product;
import library.uz.springbootwithjpa.model.dto.ProductCreateDto;
import library.uz.springbootwithjpa.service.FileServise;
import library.uz.springbootwithjpa.service.ProductServise;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final FileServise fileServise;
    private final ProductServise servise;

    @PostMapping
    public String addProduct(@ModelAttribute ProductCreateDto createDto, @RequestParam("file") MultipartFile file) throws IOException {
        String imageUrl = fileServise.upload(file);
        servise.addProduct(createDto,imageUrl);
        return "redirect:/admin/products";
    }

    @GetMapping("{categoryId}")
    @ResponseBody
    public List<Product> getProducts(@PathVariable("categoryId") int id){
        return servise.getProducts(id);
    }

}
