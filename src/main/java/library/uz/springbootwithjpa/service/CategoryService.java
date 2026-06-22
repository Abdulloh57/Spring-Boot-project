package library.uz.springbootwithjpa.service;

import library.uz.springbootwithjpa.dto.request.CategoryCreateDto;
import library.uz.springbootwithjpa.dto.response.CategoryResponceDto;
import library.uz.springbootwithjpa.model.Category;
import java.util.List;

public interface CategoryService {

    CategoryResponceDto addTopCategory(String name);

    CategoryResponceDto addCategory(CategoryCreateDto req);

    List<CategoryResponceDto> getTopcategories();

    List<CategoryResponceDto> getInnerCategories(int parentId);

    Category getById(int id);

}
