package library.uz.springbootwithjpa.service;

import jakarta.transaction.Transactional;
import library.uz.springbootwithjpa.dao.CartItemRepository;
import library.uz.springbootwithjpa.dao.CartRepository;
import library.uz.springbootwithjpa.dao.ProductRepository;
import library.uz.springbootwithjpa.dto.request.CartItemRequest;
import library.uz.springbootwithjpa.dto.response.CartItemResponse;
import library.uz.springbootwithjpa.dto.response.CategoryResponceDto;
import library.uz.springbootwithjpa.dto.response.ProductResponseDto;
import library.uz.springbootwithjpa.exception.RecordNotFoundException;
import library.uz.springbootwithjpa.model.Cart;
import library.uz.springbootwithjpa.model.CartItem;
import library.uz.springbootwithjpa.model.Product;
import library.uz.springbootwithjpa.model.enums.CartStatus;
import library.uz.springbootwithjpa.dto.response.CartItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CartServise {

    public Cart createCart(Long chatId);

    public Cart getCartByChatId(Long chatId);

    public CartItemResponse addCartItem(CartItemRequest req);

    public List<CartItem> getCartItemsByCartId(int cartId);

    public List<CartItemDto> getCartProducts(int cartId);

    public String[][] makeString(List<CartItemDto> cartProducts) ;

    public void deleteCartItem(int id);

    public void updateStatusCart(int cartId , CartStatus status) ;

    public int cartSize(int id);

}
