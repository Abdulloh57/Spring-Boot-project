package library.uz.springbootwithjpa.dao;

import library.uz.springbootwithjpa.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order , Integer> {
    @Query("""
    select o
    from Order o
    where o.user.chatId = :chatId
    """)
    List<Order> findOrdersByChatId(@Param("chatId") Long chatId);

    List<Order> findOrdersByUser_Id(int userId);
}
