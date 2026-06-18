package library.uz.springbootwithjpa.controller;

import library.uz.springbootwithjpa.dto.request.CategoryCreateDto;
import library.uz.springbootwithjpa.dto.response.CategoryResponceDto;
import library.uz.springbootwithjpa.service.CategoryService;
import library.uz.springbootwithjpa.service.ProductServise;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryServise;
    private final ProductServise productServise;

    @GetMapping
    public List<CategoryResponceDto> getTopCategories(){
        return categoryServise.getTopcategories();
    }

    @GetMapping("/{id}")
    public List<CategoryResponceDto> getInnerCategories(@PathVariable int id){
        return categoryServise.getInnerCategories(id);
    }
    @PostMapping
    public CategoryResponceDto create(@RequestBody CategoryCreateDto req){
        if (req.parentId() == null){
            return categoryServise.addTopCategory(req.name());
        }
        return categoryServise.addCategory(req);
    }
}
