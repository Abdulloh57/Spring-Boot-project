package library.uz.springbootwithjpa.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartItemDto {
    private int cartItemId;
    private int productId;
    private String name;
    private double price;
    private int quantity;
    private int productQuantity;
}
