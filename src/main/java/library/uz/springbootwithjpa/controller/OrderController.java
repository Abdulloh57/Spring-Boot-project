package library.uz.springbootwithjpa.controller;

import library.uz.springbootwithjpa.model.Order;
import library.uz.springbootwithjpa.dto.response.OrderItemDto;
import library.uz.springbootwithjpa.dto.request.OrderStatusDto;
import library.uz.springbootwithjpa.service.OrderServise;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("orders/")
@RequiredArgsConstructor
public class OrderController {
    private final OrderServise orderServise;
    @GetMapping
    @ResponseBody
    public List<Order> orders(){
        return orderServise.getAll();
    }

    @PutMapping("/{id}")
    @ResponseBody
    public void updateStatus(
            @PathVariable Integer id,
            @RequestBody OrderStatusDto dto
    ){
        orderServise.updateStatus(id,
                dto.getStatus());
    }

    @GetMapping("/{id}")
    public String getItems(@PathVariable Integer id, Model model){
        List<OrderItemDto> orderProducts = orderServise.getOrderProducts(id);
        model.addAttribute("items" ,orderProducts);

        return "orderItems";

    }
}
