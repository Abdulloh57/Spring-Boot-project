package library.uz.springbootwithjpa.TGbot.handlers;

import jakarta.transaction.Transactional;
import library.uz.springbootwithjpa.TGbot.sender.TelegramSender;
import library.uz.springbootwithjpa.TGbot.states.StateManager;
import library.uz.springbootwithjpa.TGbot.states.UserState;
import library.uz.springbootwithjpa.TGbot.util.BotUtil;
import library.uz.springbootwithjpa.TGbot.util.InlineKeyboardFactory;
import library.uz.springbootwithjpa.dto.request.CartItemRequest;
import library.uz.springbootwithjpa.dto.response.CartItemResponse;
import library.uz.springbootwithjpa.dto.response.ProductResponseDto;
import library.uz.springbootwithjpa.model.Cart;
import library.uz.springbootwithjpa.model.CartItem;
import library.uz.springbootwithjpa.service.CartServise;
import library.uz.springbootwithjpa.service.ProductServise;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.io.File;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductHandler {


    private final ProductServise productServise;
    private final TelegramSender telegramSender;
    private final StateManager stateManager;
    private final CartServise cartServise;

    public SendPhoto sendProduct(Long chatId , String callbackdata){
        final String BASE_PATH ="C:/Users/Abdulloh/Desktop/spring-boot-with-jpa/images/";
        int id = Integer.parseInt(callbackdata);
        ProductResponseDto product = productServise.getProduct(id);
        int qty = stateManager.getState(chatId).getQuantity();
        qty = qty != 0 ? qty : 1;
        InlineKeyboardMarkup inlineKeyboardMarkup = InlineKeyboardFactory.makeProduct(product, qty);

        String text = "name: " + product.getName() +
                "\nPrice: " + product.getPrice()+
                "\nQuantity: "+product.getQuantity();

        InputFile inputFile = new InputFile(
                new File(BASE_PATH+product.getImageUrl()));
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setReplyMarkup(inlineKeyboardMarkup);
        sendPhoto.setChatId(chatId);
        sendPhoto.setCaption(text);
        sendPhoto.setPhoto(inputFile);
        stateManager.updateState(chatId, UserState.SHOW_PRODUCT,id);
        return sendPhoto;
    }

    public InlineKeyboardMarkup handlerincrement(Long chatId, String callbackdata, boolean inc){
        int productId = Integer.parseInt(callbackdata.substring(3));
        ProductResponseDto product = productServise.getProduct(productId);
        InlineKeyboardMarkup inlineKeyboardMarkup = null;
        int quantity = stateManager.getState(chatId).getQuantity();
        if (inc){
            inlineKeyboardMarkup = InlineKeyboardFactory.makeProduct(product, quantity + 1);
            stateManager.getState(chatId).setQuantity(quantity+1);
        }else {
            inlineKeyboardMarkup = InlineKeyboardFactory.makeProduct(product, quantity - 1);
            stateManager.getState(chatId).setQuantity(quantity-1);
        }
        return inlineKeyboardMarkup;
    }

    @Transactional
    public SendMessage handleAddCart(Long chatId, String callbackdata){
        int productId = Integer.parseInt(callbackdata.substring(3));
        int quantity = stateManager.getState(chatId).getQuantity();
        Cart cart = cartServise.getCartByChatId(chatId);
        if (cart == null) {
           cart = cartServise.createCart(chatId);
        }
        CartItemRequest cartItemRequest = new CartItemRequest(productId, cart.getId(), quantity);
        CartItemResponse cartItemResponse = cartServise.addCartItem(cartItemRequest);
        int cartSize =  cartServise.cartSize(cart.getId());
        if (cartItemResponse == null){
            return telegramSender
                    .makeSendMessage(chatId,
                            "Product soni yetmaydi",
                            BotUtil.createReplyKeyboardMarkup(new String[]{"Orders","Cart ("+cartSize+")"},2));
        }
        stateManager.getState(chatId).setQuantity(1);
        return telegramSender.makeSendMessage(chatId,"Savatga qo'shildi", BotUtil.createReplyKeyboardMarkup(new String[]{"Orders","Cart ("+cartSize+")"},2));
    }
    @Transactional
    public SendMessage backToProduts(Long chatId, String callbackdata) {
        int productId = Integer.parseInt(callbackdata.substring(3));
        ProductResponseDto product = productServise.getProduct(productId);
        List<ProductResponseDto> products = productServise.getProducts(product.getCategoryId());
        stateManager.updateState(chatId , UserState.SHOW_PRODUCTS,product.getCategoryId());
        return telegramSender.makeSendMessage(chatId,"Products" ,BotUtil.cfg(products,4));
    }
}
