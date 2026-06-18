package library.uz.springbootwithjpa.controller;

import library.uz.springbootwithjpa.dto.request.OrderStatusDto;
import library.uz.springbootwithjpa.dto.response.OrderResponceDto;
import library.uz.springbootwithjpa.dto.response.ProductResponseDto;
import library.uz.springbootwithjpa.service.OrderServise;
import library.uz.springbootwithjpa.service.ProductServise;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {
    private final OrderServise orderServise;
    private final ProductServise productServise;
    @GetMapping
    public List<OrderResponceDto> getOrders(){
        return orderServise.getAll();
    }

    @GetMapping("/{userId}/orders")
    public List<OrderResponceDto> getByUserId(@PathVariable int userId){
        return orderServise.getOrdersByUserId(userId);
    }

    @GetMapping("/{orderId}/products")
    public List<ProductResponseDto> getProducts(@PathVariable Integer orderId){
        return productServise.getProducts(orderId);
    }

    @PutMapping("/{id}")
    public OrderResponceDto changeStatus(@PathVariable Integer id, @RequestBody OrderStatusDto status){
        return orderServise.updateStatus(id, status.getStatus());
    }
}
