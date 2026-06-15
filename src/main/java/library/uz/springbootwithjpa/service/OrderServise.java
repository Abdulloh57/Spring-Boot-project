package library.uz.springbootwithjpa.service;


import jakarta.transaction.Transactional;
import library.uz.springbootwithjpa.dao.OrderItemRepository;
import library.uz.springbootwithjpa.dao.OrderRepository;
import library.uz.springbootwithjpa.exception.RecordNotFoundException;
import library.uz.springbootwithjpa.model.*;
import library.uz.springbootwithjpa.model.dto.CartItemDto;
import library.uz.springbootwithjpa.model.dto.OrderItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServise {
    private final OrderRepository orderRepository;
    private final CartServise cartServise;
    private final ProductServise productServise;

    private final OrderItemRepository orderItemRepository;


    public List<Order> getOrdersByChatId(Long chatId){
        return orderRepository.findOrdersByChatId(chatId);
    }

    public List<Order> getOrdersByUserId(int userId){
        return orderRepository.findOrdersByUser_Id(userId);
    }

    @Transactional
    public List<Order> getAll(){
        return orderRepository.findAll();
    }

    public void addOrder(Integer userId, int cartId){
        List<CartItemDto> cartProducts = cartServise.getCartProducts(cartId);

        double totalPrice = 0.0;
        for (CartItemDto item: cartProducts){
            totalPrice+= item.getPrice()*item.getQuantity();
        }

        Order order = new Order();
        order.setUser(new User(userId));
        order.setTotalPrice(totalPrice);
        order.setStatus(OrderStatus.CREATED);
        order.setCreateAt(LocalDateTime.now());

         orderRepository.save(order);


        List<OrderItem> orderItems = cartProducts.stream()
                .map( item -> new OrderItem(
                        0,
                        new Order(order.getId()),
                        new Product(item.getProductId()),
                        item.getQuantity()
                )).toList();


        //order itemlarni qo'shish jarayoni
        orderItemRepository.saveAll(orderItems);

        //product quantitylarni kamaytirish
        productServise.changStock(order.getId(), true);
        cartServise.updateStatusCart(cartId , CartStatus.ORDERED);
    }

    @Transactional
    public void updateStatus(Integer id, OrderStatus status) {
        Optional<Order> byId = orderRepository.findById(id);
        if (byId.isEmpty()){
            throw new RecordNotFoundException("Order not found " , id);
        }
        byId.get().setStatus(status);
        if (status == OrderStatus.CANCELED ){
            productServise.changStock(id ,false);
        }

    }



    public List<OrderItemDto> getOrderProducts(Integer id) {
        return orderItemRepository.getOrderProducts(id);
    }
}
