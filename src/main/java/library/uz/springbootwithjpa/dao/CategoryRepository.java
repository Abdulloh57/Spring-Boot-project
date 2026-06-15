package library.uz.springbootwithjpa.dao;

import library.uz.springbootwithjpa.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category ,Integer> {
    List<Category> findByCategoryIsNull();

    List<Category> findByCategoryEquals(Category category);
}
