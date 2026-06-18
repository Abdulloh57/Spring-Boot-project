package library.uz.springbootwithjpa.dto.response;

import library.uz.springbootwithjpa.model.CartItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CartItemResponse {
    private int id;
    private int productId;
    private int cartId;
    private int quantity;

    public CartItemResponse(CartItem item) {
        this.id = item.getId();
        this.productId = item.getProduct().getId();
        this.cartId = item.getCart().getId();
        this.quantity = item.getQuantity();
    }
}

