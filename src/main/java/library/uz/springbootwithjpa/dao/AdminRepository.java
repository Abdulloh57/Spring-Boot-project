package library.uz.springbootwithjpa.dao;

import library.uz.springbootwithjpa.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminRepository extends JpaRepository<Admin , Integer> {
    Admin findByUsernameAndPassword(String username, String password);
}
