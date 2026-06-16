package library.uz.springbootwithjpa.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderItemDto {

    private int productId;
    private String productName;
    private double price;
    private int quantity;
}
