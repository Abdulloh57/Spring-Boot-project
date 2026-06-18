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

@Service
@RequiredArgsConstructor
public class CartServise {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

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

    public CartItemResponse addCartItem(CartItemRequest req){
        Product product = productRepository.findById(req.getProductId()).orElseThrow(
                () ->  new RecordNotFoundException("Product not found")
        );
        if (product.getQuantity() < req.getQuantity()){
            return null;
        }
        CartItem cartItem = cartItemRepository.findByCart_IdAndProduct_Id(req.getCartId(), req.getProductId());
        if (cartItem != null){
            cartItem.setQuantity(cartItem.getQuantity() + req.getQuantity());
            return new CartItemResponse(cartItem);
        }
        Cart cart = cartRepository.findById(req.getCartId()).orElseThrow(
                () -> new RecordNotFoundException("Cart not found")
        );
        CartItem save = cartItemRepository.save(new CartItem(product, cart, req.getQuantity()));
        return new CartItemResponse(
                save.getId(),
                save.getProduct().getId(),
                save.getCart().getId(),
                save.getQuantity());
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

    public void deleteCartItem(int id) {
        cartItemRepository.deleteById(id);
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

    public int cartSize(int id) {
       return cartItemRepository.countCartItemByCart_Id(id);
    }
}
