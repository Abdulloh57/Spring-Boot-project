package library.uz.springbootwithjpa.service;

import jakarta.transaction.Transactional;
import library.uz.springbootwithjpa.dao.CategoryRepository;
import library.uz.springbootwithjpa.exception.RecordNotFoundException;
import library.uz.springbootwithjpa.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServise {
    private final CategoryRepository categoryRepository;

    public void addTopCategory(String name){
        Category category = new Category();
        category.setName(name);
        categoryRepository.save(category);
    }
    @Transactional
    public void addCategory(String name ,int parentId){
        Category category = new Category();
        category.setName(name);
        Optional<Category> parent = categoryRepository.findById(parentId);
        if (parent.isEmpty()){
            throw new RecordNotFoundException("Parent category not found" , parentId);
        }
        category.setCategory(parent.get());
        categoryRepository.save(category);
    }

    public List<Category> getTopcategories(){
        return categoryRepository.findByCategoryIsNull();
    }

    public List<Category> getInnerCategories(int parentId){
        Optional<Category> parent = categoryRepository.findById(parentId);
        if (parent.isEmpty()){
            throw new RecordNotFoundException("Parent category not found" , parentId);
        }
        return categoryRepository.findByCategoryEquals(parent.get());
    }

    public Category getById(int id) {
        Optional<Category> byId = categoryRepository.findById(id);
        return byId.orElse(null);
    }
}
