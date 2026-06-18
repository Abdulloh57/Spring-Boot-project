package library.uz.springbootwithjpa.TGbot.handlers;

import library.uz.springbootwithjpa.TGbot.util.BotUtil;
import library.uz.springbootwithjpa.TGbot.sender.TelegramSender;
import library.uz.springbootwithjpa.TGbot.states.StateManager;
import library.uz.springbootwithjpa.TGbot.states.UserState;
import library.uz.springbootwithjpa.dto.response.CategoryResponceDto;
import library.uz.springbootwithjpa.dto.response.ProductResponseDto;
import library.uz.springbootwithjpa.model.Product;
import library.uz.springbootwithjpa.service.CategoryService;
import library.uz.springbootwithjpa.service.ProductServise;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryHandler {
    private final CategoryService categoryServise;
    private final ProductServise productServise;
    private final TelegramSender telegramSender;
    private final StateManager stateManager;
    /// BU uchun best case deb harbir bir state uchun alohida method yaratishni maslahat berdi
    /// chatGpt o'zim boshida bitta metod qilib stateni tekshirishim kerakmi deb o'ylanayotgan edim
    public SendMessage handlerStart(Long chatId, String callBackData){
        int id = Integer.parseInt(callBackData);
        List<CategoryResponceDto> innerCategories = categoryServise.getInnerCategories(id);
        if (innerCategories.isEmpty()){
           return telegramSender.makeSendMessage(chatId,"There is no child categories" , BotUtil.cfg(categoryServise.getTopcategories(),4));
        }else {
           stateManager.updateState(chatId,UserState.SHOW_CATEGORY , id);
           return telegramSender.makeSendMessage(chatId,"Child categories",BotUtil.cfg(innerCategories,4));
        }
    }

    public SendMessage handlerShowCategory(Long chatId, String callBackData){
        int id = Integer.parseInt(callBackData);
        List<ProductResponseDto> products = productServise.getProducts(id);
        if (products.isEmpty()) {
            return telegramSender.makeSendMessage(chatId,"There is no products" , null);
        }
        else {
            stateManager.updateState(chatId , UserState.SHOW_PRODUCTS,id);
            return telegramSender.makeSendMessage(chatId,"Products" ,BotUtil.cfg(products,4));
        }
    }
}
