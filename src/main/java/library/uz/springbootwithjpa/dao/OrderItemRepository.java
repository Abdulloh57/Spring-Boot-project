package library.uz.springbootwithjpa.dao;

import library.uz.springbootwithjpa.model.OrderItem;
import library.uz.springbootwithjpa.model.dto.OrderItemDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem , Integer> {
    @Query("""
            select new library.uz.springbootwithjpa.model.dto.OrderItemDto(
                p.id,
                p.name,
                p.price,
                oi.quantity
            )
            from OrderItem oi
            join oi.product p
            where oi.order.id = :orderId
            """)
    List<OrderItemDto> getOrderProducts(@Param("orderId") Integer id);

    List<OrderItem> findByOrder_Id(int orderId);
}
