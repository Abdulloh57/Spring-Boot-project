package library.uz.springbootwithjpa.service;

import library.uz.springbootwithjpa.dto.response.OrderResponceDto;
import library.uz.springbootwithjpa.dto.response.OrderItemDto;
import library.uz.springbootwithjpa.model.enums.OrderStatus;
import java.util.List;

public interface OrderServise {

    List<OrderResponceDto> getOrdersByChatId(Long chatId);

    List<OrderResponceDto> getOrdersByUserId(int userId);

    List<OrderResponceDto> getAll();

    OrderResponceDto addOrder(Integer userId, int cartId);

    OrderResponceDto updateStatus(Integer id, OrderStatus status);

    List<OrderItemDto> getOrderProducts(Integer id);

}
