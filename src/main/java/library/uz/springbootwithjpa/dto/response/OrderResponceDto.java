package library.uz.springbootwithjpa.dto.response;

import library.uz.springbootwithjpa.model.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Builder
public class OrderResponceDto {
    private int id;
    private int userId;
    private double totalPrice;
    private OrderStatus orderStatus;
    private LocalDateTime createAt;
}
