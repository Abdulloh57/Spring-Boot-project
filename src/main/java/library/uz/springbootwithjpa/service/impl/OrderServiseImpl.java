package library.uz.springbootwithjpa.service.impl;

import jakarta.transaction.Transactional;
import library.uz.springbootwithjpa.dao.OrderItemRepository;
import library.uz.springbootwithjpa.dao.OrderRepository;
import library.uz.springbootwithjpa.dto.response.CartItemDto;
import library.uz.springbootwithjpa.dto.response.OrderItemDto;
import library.uz.springbootwithjpa.dto.response.OrderResponceDto;
import library.uz.springbootwithjpa.exception.RecordNotFoundException;
import library.uz.springbootwithjpa.model.Order;
import library.uz.springbootwithjpa.model.OrderItem;
import library.uz.springbootwithjpa.model.Product;
import library.uz.springbootwithjpa.model.User;
import library.uz.springbootwithjpa.model.enums.CartStatus;
import library.uz.springbootwithjpa.model.enums.OrderStatus;
import library.uz.springbootwithjpa.service.CartServise;
import library.uz.springbootwithjpa.service.OrderServise;
import library.uz.springbootwithjpa.service.ProductServise;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiseImpl implements OrderServise {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartServise cartServise;
    private final ProductServise productServise;

    @Override
    public List<OrderResponceDto> getOrdersByChatId(Long chatId){
        return orderRepository.findOrdersByChatId(chatId);
    }

    @Override
    public List<OrderResponceDto> getOrdersByUserId(int userId){
        return orderRepository.findOrdersByUser_Id(userId).stream().map(this::mapOrder).toList();
    }

    @Override
    @Transactional
    public List<OrderResponceDto> getAll(){
        return orderRepository.findAll().stream().map(this::mapOrder
        ).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrderResponceDto addOrder(Integer userId, int cartId){
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

        Order save = orderRepository.save(order);


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
        productServise.changStock(save.getId(), true);
        cartServise.updateStatusCart(cartId , CartStatus.ORDERED);
        return mapOrder(save);
    }

    @Override
    @Transactional
    public OrderResponceDto updateStatus(Integer id, OrderStatus status) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new RecordNotFoundException("Order not found " , id));

        order.setStatus(status);
        if (status == OrderStatus.CANCELED ){
            productServise.changStock(id ,false);
        }
        return mapOrder(order);

    }


    @Override
    public List<OrderItemDto> getOrderProducts(Integer id) {
        return orderItemRepository.getOrderProducts(id);
    }

    private OrderResponceDto  mapOrder(Order order){
        return new OrderResponceDto(
                order.getId(),
                order.getUser().getId(),
                order.getTotalPrice(),
                order.getStatus(),
                order.getCreateAt()
        );
    }
}
