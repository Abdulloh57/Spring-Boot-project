package library.uz.springbootwithjpa.controller;

import jakarta.servlet.http.HttpSession;

import library.uz.springbootwithjpa.model.Admin;
import library.uz.springbootwithjpa.model.Category;
import library.uz.springbootwithjpa.dto.request.CategoryCreateDto;
import library.uz.springbootwithjpa.service.CategoryServise;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryServise servise;

    @PostMapping
    public String addCategory(@ModelAttribute CategoryCreateDto request , Model model , HttpSession session){
        Admin admin = (Admin) session.getAttribute("admin");
        model.addAttribute("admin" ,admin);

        if (request.parentId() == null) {
            servise.addTopCategory(request.name());

        }else  servise.addCategory(request.name(), request.parentId());

        model.addAttribute("topCategories",servise.getTopcategories());
        model.addAttribute(
                "category",
                new CategoryCreateDto(null,null)
        );
        return "products";

    }
    @GetMapping
    @ResponseBody
    public List<Category> getInnerCategories(){
        return servise.getTopcategories();
    }

    @GetMapping("{parentId}")
    @ResponseBody
    public List<Category> getInnerCategories(@PathVariable("parentId") int parentId){
        return servise.getInnerCategories(parentId);
    }



}
