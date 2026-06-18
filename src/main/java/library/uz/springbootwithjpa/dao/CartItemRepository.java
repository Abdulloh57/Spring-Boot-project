package library.uz.springbootwithjpa.dao;

import library.uz.springbootwithjpa.model.Cart;
import library.uz.springbootwithjpa.model.CartItem;
import library.uz.springbootwithjpa.dto.response.CartItemDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    List<CartItem> findByCart(Cart cart);
    @Query("""
            select new library.uz.springbootwithjpa.dto.response.CartItemDto(
                c.id,
                p.id,
                p.name,
                p.price,
                c.quantity,
                p.quantity
            )
            from CartItem c
            join c.product p
            where c.cart = :cart
            """)
    List<CartItemDto> getCartItemByCart(@Param("cart") Cart cart);

    CartItem findByCart_IdAndProduct_Id(int cartId, int productId);

    int countCartItemByCart_Id(int cartId);
}
