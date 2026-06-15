package library.uz.springbootwithjpa.TGbot.handlers;

import library.uz.springbootwithjpa.TGbot.sender.TelegramSender;
import library.uz.springbootwithjpa.TGbot.states.StateManager;
import library.uz.springbootwithjpa.TGbot.states.UserState;
import library.uz.springbootwithjpa.TGbot.util.BotUtil;
import library.uz.springbootwithjpa.model.dto.CartItemDto;
import library.uz.springbootwithjpa.service.CartServise;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartHandle {
    private final CartServise cartServise;
    private final TelegramSender telegramSender;
    private final StateManager stateManager;

    public SendMessage handleShowCart(Long chatId, String callbackdata){
        int cartId = Integer.parseInt(callbackdata.substring(1));
        List<CartItemDto> cartProducts = cartServise.getCartProducts(cartId);
        String deleteText = BotUtil.showCart(cartProducts);
        stateManager.updateState(chatId,UserState.DELETE_CART_ITEM,cartId);
        return telegramSender.makeSendMessage(chatId,deleteText,BotUtil.getInlineRow(cartServise.makeString(cartProducts)));
    }

    public SendMessage handleDelete(Long chatId,int cartItemId) {
        cartServise.deleteCartItem(cartItemId);
        Integer cartId = stateManager.getState(chatId).getCurrentRecordId();
        List<CartItemDto> cartProducts = cartServise.getCartProducts(cartId);
        String deleteText = BotUtil.showCart(cartProducts);
        stateManager.updateState(chatId,UserState.DELETE_CART_ITEM,cartId);
        return telegramSender.makeSendMessage(chatId,deleteText,BotUtil.getInlineRow(cartServise.makeString(cartProducts)));
    }
}
