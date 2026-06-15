package library.uz.springbootwithjpa.dao;

import library.uz.springbootwithjpa.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    Cart findByChatId(Long chatId);
}
