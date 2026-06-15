package library.uz.springbootwithjpa.dao;

import library.uz.springbootwithjpa.model.Category;
import library.uz.springbootwithjpa.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product , Integer> {
    List<Product> findByCategory(Category category);
}
