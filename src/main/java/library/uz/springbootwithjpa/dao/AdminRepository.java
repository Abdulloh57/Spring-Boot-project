package library.uz.springbootwithjpa.dao;

import library.uz.springbootwithjpa.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin , Integer> {
    Admin findByUsernameAndPassword(String username, String password);

    Optional<Admin> findByUsername(String username);
}
