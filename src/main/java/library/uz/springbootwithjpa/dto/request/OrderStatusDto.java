package library.uz.springbootwithjpa.dto.request;


import library.uz.springbootwithjpa.model.enums.OrderStatus;
import lombok.Data;

@Data
public class OrderStatusDto {
    private OrderStatus status;
}