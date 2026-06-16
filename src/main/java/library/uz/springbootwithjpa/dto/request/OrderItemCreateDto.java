package library.uz.springbootwithjpa.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderItemCreateDto {
    private int productId;
    private int quantity;
    private int productQuantity;
}
