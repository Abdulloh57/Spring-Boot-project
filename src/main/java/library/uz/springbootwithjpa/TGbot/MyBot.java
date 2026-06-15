package library.uz.springbootwithjpa.TGbot;

import library.uz.springbootwithjpa.TGbot.handlers.*;
import library.uz.springbootwithjpa.TGbot.states.StateManager;
import library.uz.springbootwithjpa.TGbot.states.UserDataState;
import library.uz.springbootwithjpa.model.*;
import library.uz.springbootwithjpa.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.*;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
@RequiredArgsConstructor
public class MyBot extends TelegramLongPollingBot {
    private final TOKEN token;
    private final MessageHandler messageHandler;
    private final CategoryHandler categoryHandler;
    private final ProductHandler productHandler;
    private final CartHandle cartHandle;
    private final OrderHandle orderHandle;
    private final StateManager stateManager;
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()){
            Message message = update.getMessage();

            SendMessage sendMessage = messageHandler.handleMessage(message);
            sendmessage(sendMessage);

        } else if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            Long chatId = callbackQuery.getMessage().getChatId();
            callBackHandler(chatId,callbackQuery);
        }
    }

    private void callBackHandler(Long chatId ,CallbackQuery callbackQuery){
        String callbackdata = callbackQuery.getData();
        System.out.println(callbackdata);
        UserDataState state = stateManager.getState(chatId);

        switch (state.getUserState()){
            case START -> {
                SendMessage sendMessage = categoryHandler.handlerStart(chatId, callbackdata);
                sendmessage(sendMessage);
            }

            case SHOW_CATEGORY -> {
                SendMessage sendMessage = categoryHandler.handlerShowCategory(chatId, callbackdata);
                sendmessage(sendMessage);
            }

            case SHOW_PRODUCTS -> {
                SendPhoto sendPhoto = productHandler.sendProduct(chatId, callbackdata);
                sendPhoto(sendPhoto);
            }

            case SHOW_PRODUCT -> {
                String prefix = callbackdata.substring(0,3);

                switch (prefix){
                    case "dec" -> {
                        Integer messageId = callbackQuery.getMessage().getMessageId();
                        InlineKeyboardMarkup inlineKeyboardMarkup =
                                productHandler.handlerincrement(chatId, callbackdata, false);
                        editMessage(chatId,messageId ,inlineKeyboardMarkup );
                    }
                    case "inc" -> {
                        Integer messageId = callbackQuery.getMessage().getMessageId();
                        InlineKeyboardMarkup inlineKeyboardMarkup =
                                productHandler.handlerincrement(chatId, callbackdata, true);
                        editMessage(chatId,messageId ,inlineKeyboardMarkup);
                    }

                    case "ADC" ->{
                        SendMessage sendMessage = productHandler.handleAddCart(chatId, callbackdata);
                        sendmessage(sendMessage);
                    }

                    case "BCK" -> {
                        SendMessage sendMessage = productHandler.backToProduts(chatId, callbackdata);
                        sendmessage(sendMessage);
                    }

                    case "NXT" -> {
                        SendMessage sendMessage = categoryHandler.handlerStart(chatId, callbackdata.substring(3));
                        sendmessage(sendMessage);
                    }
                }
            }

            case SHOW_CART -> {
                SendMessage sendMessage;
                if (callbackdata.startsWith("D")){
                    sendMessage = cartHandle.handleShowCart(chatId, callbackdata);
                }else{
                    sendMessage = orderHandle.orderHandle(chatId, callbackdata);
                }
                sendmessage(sendMessage);
            }

            case DELETE_CART_ITEM -> {
                int cartItemId = Integer.parseInt(callbackdata);
                SendMessage sendMessage = cartHandle.handleDelete(chatId, cartItemId);
                sendmessage(sendMessage);
            }
        }
    }
    ///
    /// Bu yangi funksiyalarni yozish uchun
    ///

    public void sendmessage(SendMessage sendMessage){
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Telegram error{}", e.getMessage());
        }
    }

    public void sendPhoto(SendPhoto sendPhoto){
        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            log.error("Telegram error{}", e.getMessage());
        }
    }

    private void editMessage (Long chatId, Integer messageId, InlineKeyboardMarkup inlineKeyboardMarkup){
        EditMessageReplyMarkup editMessageReplyMarkup =
                new EditMessageReplyMarkup();
        editMessageReplyMarkup.setChatId(chatId);
        editMessageReplyMarkup.setMessageId(messageId);
        editMessageReplyMarkup.setReplyMarkup(inlineKeyboardMarkup);

        try {
            execute(editMessageReplyMarkup);
        } catch (TelegramApiException e) {
            log.error("Telegram error{}", e.getMessage());
        }
    }

    @Override
    public String getBotUsername() { return token.getBOT_USERNAME(); }

    @Override
    public String getBotToken() { return token.getBOT_TOKEN(); }
}
