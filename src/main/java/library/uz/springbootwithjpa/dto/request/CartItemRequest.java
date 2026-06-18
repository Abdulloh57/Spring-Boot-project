package library.uz.springbootwithjpa.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CartItemRequest {
    private int productId;
    private int cartId;
    private int quantity;
}
