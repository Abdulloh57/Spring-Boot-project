package library.uz.springbootwithjpa.TGbot.handlers;

import jakarta.transaction.Transactional;
import library.uz.springbootwithjpa.TGbot.sender.TelegramSender;
import library.uz.springbootwithjpa.TGbot.states.StateManager;
import library.uz.springbootwithjpa.TGbot.states.UserDataState;
import library.uz.springbootwithjpa.TGbot.states.UserState;
import library.uz.springbootwithjpa.TGbot.util.BotUtil;
import library.uz.springbootwithjpa.TGbot.util.InlineKeyboardFactory;
import library.uz.springbootwithjpa.exception.RecordNotFoundException;
import library.uz.springbootwithjpa.model.Cart;
import library.uz.springbootwithjpa.model.CartItem;
import library.uz.springbootwithjpa.model.CartStatus;
import library.uz.springbootwithjpa.model.Product;
import library.uz.springbootwithjpa.service.CartServise;
import library.uz.springbootwithjpa.service.ProductServise;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.io.File;
import java.time.LocalDateTime;
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
        Product product = productServise.getProduct(id);
        if (product == null){
            throw new RecordNotFoundException("Product not found", id);
        }
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
        Product product = productServise.getProduct(productId);
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
        Product product = productServise.getProduct(productId);
        int quantity = stateManager.getState(chatId).getQuantity();
        Cart cart = cartServise.getCartByChatId(chatId);
        if (cart == null) {
           cart = cartServise.createCart(chatId);
        }
        if (product.getQuantity() < quantity){
            return telegramSender.makeSendMessage(chatId, "ProductSoni yetmaydi", null);
        }
        List<CartItem> cartItems = cartServise.getCartItemsByCartId(cart.getId());
        for (CartItem item : cartItems){
            if (item.getProduct().getId() == productId){
                item.setQuantity(item.getQuantity()+ quantity);
                cartServise.addCartItem(item);
                if (product.getQuantity() < item.getQuantity()+quantity){
                    return telegramSender.makeSendMessage(chatId, "ProductSoni yetmaydi", null);
                }else{
                    stateManager.getState(chatId).setQuantity(1);
                    return telegramSender.makeSendMessage(chatId,"Savatga qo'shildi", BotUtil.createReplyKeyboardMarkup(new String[]{"Orders","Cart ("+cartItems.size()+")"},2));

                }
            }
        }
        CartItem cartItem  =CartItem.builder().cart(cart).product(product).build();
        cartServise.addCartItem(cartItem);
        stateManager.getState(chatId).setQuantity(1);
        return telegramSender.makeSendMessage(chatId,"Savatga qo'shildi", BotUtil.createReplyKeyboardMarkup(new String[]{"Orders","Cart ("+cartItems.size()+")"},2));
    }
    @Transactional
    public SendMessage backToProduts(Long chatId, String callbackdata) {
        int productId = Integer.parseInt(callbackdata.substring(3));
        Product product = productServise.getProduct(productId);
        List<Product> products = productServise.getProducts(product.getCategory().getId());
        if (products.isEmpty()) {
            return telegramSender.makeSendMessage(chatId,"There is no products" , null);
        }
        else {
            stateManager.updateState(chatId , UserState.SHOW_PRODUCTS,product.getCategory().getId());
            return telegramSender.makeSendMessage(chatId,"Products" ,BotUtil.ctg(products,4));
        }
    }
}
