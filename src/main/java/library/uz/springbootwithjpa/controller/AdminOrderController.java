package library.uz.springbootwithjpa.controller;

import library.uz.springbootwithjpa.dto.request.OrderStatusDto;
import library.uz.springbootwithjpa.dto.response.OrderItemDto;
import library.uz.springbootwithjpa.dto.response.OrderResponceDto;
import library.uz.springbootwithjpa.service.OrderServise;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {
    private final OrderServise orderServise;
    @GetMapping
    public List<OrderResponceDto> getOrders(){
        return orderServise.getAll();
    }

    @GetMapping("/{userId}/orders")
    public List<OrderResponceDto> getByUserId(@PathVariable int userId){
        return orderServise.getOrdersByUserId(userId);
    }

    @GetMapping("/{orderId}/products")
    public List<OrderItemDto> getProducts(@PathVariable Integer orderId){
        return orderServise.getOrderProducts(orderId);
    }

    @PutMapping("/{id}")
    public OrderResponceDto changeStatus(@PathVariable Integer id, @RequestBody OrderStatusDto status){
        return orderServise.updateStatus(id, status.getStatus());
    }
}
