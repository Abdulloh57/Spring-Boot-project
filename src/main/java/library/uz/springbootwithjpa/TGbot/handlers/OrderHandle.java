package library.uz.springbootwithjpa.TGbot.handlers;

import library.uz.springbootwithjpa.TGbot.sender.TelegramSender;
import library.uz.springbootwithjpa.TGbot.states.StateManager;
import library.uz.springbootwithjpa.TGbot.util.BotUtil;
import library.uz.springbootwithjpa.model.User;
import library.uz.springbootwithjpa.dto.response.CartItemDto;
import library.uz.springbootwithjpa.service.CartServise;
import library.uz.springbootwithjpa.service.OrderServise;
import library.uz.springbootwithjpa.service.UserServise;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderHandle {
    private final StateManager stateManager;
    private final CartServise cartServise;
    private final TelegramSender telegramSender;
    private final UserServise userServise;
    private final OrderServise orderServise;

    public SendMessage orderHandle(Long chatId, String callbackData){
        int cartId = stateManager.getState(chatId).getCurrentRecordId();
        List<CartItemDto> cartProducts = cartServise.getCartProducts(cartId);

        //Bu qaysi productlarni soni yetmaydi shuni bilish uchun va xabardor qilish uchun bu haqida
        List<CartItemDto> notApproved = cartProducts.stream().filter(item -> item.getQuantity() > item.getProductQuantity()).toList();
        if (!notApproved.isEmpty()){
            String s = BotUtil.showCart(notApproved);
           return telegramSender.makeSendMessage(chatId, s+"\n————————————Bu productlar soni yetmaydi",
                    BotUtil.getInlineRow(
                            new String[][]{{"Delete Product","D"+cartId}}));
        }

        User user = userServise.getUser(chatId);
        if (user.getPhoneNumber() == null){
            return telegramSender.makeSendMessage(chatId,"Avval to'liq ro'yxatdan o'ting",BotUtil.requestContact());
        }
        orderServise.addOrder(user.getId(), cartId);

        return telegramSender.makeSendMessage(chatId,"order muvaffaqiyatli yaratildi" ,
                BotUtil.createReplyKeyboardMarkup(new String[]{"Orders","Cart (0)"},2));
    }
}
