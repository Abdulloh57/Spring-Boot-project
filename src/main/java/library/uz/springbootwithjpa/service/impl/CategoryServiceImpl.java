package library.uz.springbootwithjpa.service.impl;

import jakarta.transaction.Transactional;
import library.uz.springbootwithjpa.dao.CategoryRepository;
import library.uz.springbootwithjpa.dto.request.CategoryCreateDto;
import library.uz.springbootwithjpa.dto.response.CategoryResponceDto;
import library.uz.springbootwithjpa.exception.RecordNotFoundException;
import library.uz.springbootwithjpa.model.Category;
import library.uz.springbootwithjpa.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryResponceDto addTopCategory(String name){
        Category category = new Category();
        category.setName(name);
        Category save = categoryRepository.save(category);
        return new CategoryResponceDto(save.getId(), save.getName(), 0);
    }

    @Override
    @Transactional
    public CategoryResponceDto addCategory(CategoryCreateDto req){
        Optional<Category> parent = categoryRepository.findById(req.parentId());
        if (parent.isEmpty()){
            throw new RecordNotFoundException("Parent category not found" , req.parentId());
        }
        Category category = new Category();
        category.setName(req.name());
        category.setCategory(parent.get());
        Category save = categoryRepository.save(category);
        return new CategoryResponceDto(save.getId(), save.getName(), 0);

    }

    @Override
    public List<CategoryResponceDto> getTopcategories(){
        return categoryRepository.findByCategoryIsNull().stream().map(ct -> CategoryResponceDto.builder()
                .id(ct.getId())
                .name(ct.getName())
                .build()).toList();
    }

    @Override
    public List<CategoryResponceDto> getInnerCategories(int parentId){
        Optional<Category> parent = categoryRepository.findById(parentId);
        if (parent.isEmpty()){
            throw new RecordNotFoundException("Parent category not found" , parentId);
        }
        return categoryRepository.findByCategoryEquals(parent.get()).stream().map(ct -> CategoryResponceDto.builder()
                .id(ct.getId())
                .name(ct.getName())
                .parentId(parentId)
                .build()).toList();
    }

    @Override
    public Category getById(int id) {
        Optional<Category> byId = categoryRepository.findById(id);
        return byId.orElse(null);
    }
}
