package library.uz.springbootwithjpa.TGbot.handlers;

import library.uz.springbootwithjpa.TGbot.util.BotUtil;
import library.uz.springbootwithjpa.TGbot.sender.TelegramSender;
import library.uz.springbootwithjpa.TGbot.states.StateManager;
import library.uz.springbootwithjpa.TGbot.states.UserDataState;
import library.uz.springbootwithjpa.TGbot.states.UserState;
import library.uz.springbootwithjpa.model.Cart;
import library.uz.springbootwithjpa.model.CartItem;
import library.uz.springbootwithjpa.model.Order;
import library.uz.springbootwithjpa.dto.response.CartItemDto;
import library.uz.springbootwithjpa.service.CartServise;
import library.uz.springbootwithjpa.service.CategoryServise;
import library.uz.springbootwithjpa.service.OrderServise;
import library.uz.springbootwithjpa.service.UserServise;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageHandler {
    private final UserServise userServise;
    private final StateManager stateManager;
    private final TelegramSender telegramSender;
    private final CategoryServise categoryServise;
    private final CartServise cartServise;
    private final OrderServise orderServise;
    public SendMessage handleMessage(Message message){

        String text = message.hasText() ? message.getText() : "";
        Long chatId = message.getChatId();
        UserDataState userDataState = stateManager.getState(chatId);
        if (userDataState == null){
            userDataState = new UserDataState(UserState.START);
            stateManager.createState(chatId, userDataState);
            userServise.firstRegistration(chatId);
            return telegramSender.makeSendMessage(chatId,"Welcome",null);
        }else if (userDataState.getUserState().equals(UserState.START)){
            return telegramSender.makeSendMessage(chatId, "Top categories", BotUtil.ctg(
                    categoryServise.getTopcategories(), 4
            ));
        }
        if (message.hasContact()){
            Contact contact = message.getContact();
            userServise.finalRegistration(chatId, contact);
            List<CartItem> cartItemsByCartId = cartServise.getCartItemsByCartId(userDataState.getCurrentRecordId());
            return telegramSender.makeSendMessage(chatId ,"Muvaffaqiyatli ro'yxatdan o'tildi",
                    BotUtil.createReplyKeyboardMarkup(
                            new String[]{"Orders","Cart ("+cartItemsByCartId.size()+")"},
                            2) );
        }

        if (text.startsWith("Cart")){
            return showCart(chatId);
        }

        if (text.equals("Orders")){
            return showOrders(chatId);
        }

        if (text.equals("/start")){
            stateManager.updateState(chatId,UserState.START ,0);
            return telegramSender.makeSendMessage(chatId,"Top categories",BotUtil.ctg(
                    categoryServise.getTopcategories(),4
            ));
        }
        return telegramSender.makeSendMessage(chatId, "Top categories", BotUtil.ctg(
                categoryServise.getTopcategories(), 4
        ));
    }

    private SendMessage showOrders(Long chatId) {
        List<Order> orders = orderServise.getOrdersByChatId(chatId);

        StringBuilder result = new StringBuilder();
        for (Order order : orders){
            result.append("\n orderId: ").append(order.getId())
                    .append("\ntotal price: ").append(order.getTotalPrice())
                    .append("\norder status: ").append(order.getStatus())
                    .append("\norder created: ").append(order.getCreateAt()).append("\n");
        }
        return telegramSender.makeSendMessage(chatId, result.toString(),null);
    }

    public SendMessage showCart(Long chatId){
        Cart cart = cartServise.getCartByChatId(chatId);
        System.out.println(cart);
        if (cart == null){
            cartServise.createCart(chatId);
            return telegramSender.makeSendMessage(chatId,"Savat bo'sh" ,null);
        }
        List<CartItemDto> cartProducts = cartServise.getCartProducts(cart.getId());
        String cartText =
                BotUtil.showCart(cartProducts);
        stateManager.updateState(chatId,UserState.SHOW_CART,cart.getId());
        return telegramSender.makeSendMessage(chatId, cartText,
                BotUtil.getInlineRow(
                        new String[][]{
                                {"Add order","A"+cart.getId()},
                                {"Delete Product","D"+cart.getId()}}));
    }
}
