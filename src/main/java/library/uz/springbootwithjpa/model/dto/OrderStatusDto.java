package library.uz.springbootwithjpa.model.dto;


import library.uz.springbootwithjpa.model.OrderStatus;
import lombok.Data;

@Data
public class OrderStatusDto {
    private OrderStatus status;
}