package library.uz.springbootwithjpa.service;

import jakarta.transaction.Transactional;
import library.uz.springbootwithjpa.dao.CartItemRepository;
import library.uz.springbootwithjpa.dao.CartRepository;
import library.uz.springbootwithjpa.exception.RecordNotFoundException;
import library.uz.springbootwithjpa.model.Cart;
import library.uz.springbootwithjpa.model.CartItem;
import library.uz.springbootwithjpa.model.CartStatus;
import library.uz.springbootwithjpa.model.dto.CartItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServise {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    public Cart createCart(Long chatId){
        Cart cart = new Cart();
        cart.setChatId(chatId);
        cart.setStatus(CartStatus.ACTIVE);
        cart.setLocalDateTime(LocalDateTime.now());
        cartRepository.save(cart);
        return cart;
    }

    public Cart getCartByChatId(Long chatId){
        return cartRepository.findByChatId(chatId);
    }
    public void addCartItem(CartItem cartItem){
        cartItemRepository.save(cartItem);
    }

    public List<CartItem> getCartItemsByCartId(int cartId){
        Optional<Cart> cart = cartRepository.findById(cartId);
        if (cart.isEmpty()){
            throw new RecordNotFoundException("Cart not found",cartId);
        }
        return cartItemRepository.findByCart(cart.get());
    }

    public List<CartItemDto> getCartProducts(int cartId){
        Optional<Cart> cart = cartRepository.findById(cartId);
        if (cart.isEmpty()){
            throw new RecordNotFoundException("Cart not found ",cartId);
        }
        return cartItemRepository.getCartItemByCart(cart.get());
    }

    public String[][] makeString(List<CartItemDto> cartProducts) {

        String[][] matrix = new String[cartProducts.size()][2];

        int i = 0;

        for (CartItemDto cartItemDao : cartProducts) {

            matrix[i] = new String[]{
                    cartItemDao.getName(),
                    String.valueOf(cartItemDao.getCartItemId())
            };

            i++;
        }

        return matrix;
    }

    public void deleteCartItem(int cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }
    @Transactional
    public void updateStatusCart(int cartId , CartStatus status) {
        Optional<Cart> find = cartRepository.findById(cartId);
        if (find.isEmpty()){
            throw new RecordNotFoundException("Cart not found ", cartId);
        }
        Cart cart = find.get();
        cart.setStatus(status);
    }

}
