package library.uz.springbootwithjpa.dao;

import library.uz.springbootwithjpa.model.User;
import library.uz.springbootwithjpa.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByChatId(Long chatId);

}
