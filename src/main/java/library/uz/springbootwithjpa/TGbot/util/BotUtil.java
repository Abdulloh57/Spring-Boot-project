package library.uz.springbootwithjpa.TGbot.util;

import library.uz.springbootwithjpa.model.Category;
import library.uz.springbootwithjpa.model.Product;
import library.uz.springbootwithjpa.dto.response.CartItemDto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class BotUtil {
    public static<T> InlineKeyboardMarkup ctg(List<T> btns,int colCount){
        InlineKeyboardMarkup inlineKeyboardMarkup
                = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        inlineKeyboardMarkup.setKeyboard(rows);

        List<InlineKeyboardButton> row = new ArrayList<>();
        int count = 0;
        for (T btn : btns) {
            InlineKeyboardButton button;
            if (btn instanceof Category category) {
                button = new InlineKeyboardButton(category.getName());
                button.setCallbackData(""+ category.getId());
            }else if (btn instanceof Product product) {
                button = new InlineKeyboardButton(product.getName());
                button.setCallbackData(""+product.getId());
            }else {
                throw new UnsupportedOperationException("");
            }
            count++;
            row.add(button);
            if (count % colCount == 0) {
                rows.add(row);
                row = new ArrayList<>();
            }
        }
        if (!row.isEmpty()) {
            rows.add(row);
        }

        return inlineKeyboardMarkup;
    }

    public static List<InlineKeyboardButton> getRow(String[][] btns){
        List<InlineKeyboardButton> row = new ArrayList<>();
        for (String[] strings : btns) {
            InlineKeyboardButton btn = new InlineKeyboardButton(strings[0]);
            btn.setCallbackData(strings[1]);
            row.add(btn);
        }
        return row;
    }


    public static InlineKeyboardMarkup getInlineRow(String[][] btns){
        InlineKeyboardMarkup inlineKeyboardMarkup
                = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        inlineKeyboardMarkup.setKeyboard(rows);
        List<InlineKeyboardButton> row = new ArrayList<>();
        for (String[] strings : btns) {
            InlineKeyboardButton btn = new InlineKeyboardButton(strings[0]);
            btn.setCallbackData(strings[1]);
            row.add(btn);
        }
        rows.add(row);
        return inlineKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup requestContact(){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        List<KeyboardRow> rows = new ArrayList<>();
        replyKeyboardMarkup.setKeyboard(rows);
        KeyboardRow row = new KeyboardRow();
        KeyboardButton keyboardButton = new KeyboardButton("Share contact");
        keyboardButton.setRequestContact(true);
        row.add(keyboardButton);
        rows.add(row);
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup createReplyKeyboardMarkup(String[] text,int rowCount){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        List<KeyboardRow> rows = new ArrayList<>();
        replyKeyboardMarkup.setKeyboard(rows);
        KeyboardRow row = new KeyboardRow();

        int count = 1;
        for (String txt : text){
            row.add(new KeyboardButton(txt));
            count++;
            if (count%rowCount == 0) {
                rows.add(row);
            }
        }

        return replyKeyboardMarkup;
    }


    public static String showCart(List<CartItemDto> cartProducts) {
        double totalPrice = 0.0;
        StringBuilder stringBuilder = new StringBuilder();
        for (CartItemDto cartItem : cartProducts){
            stringBuilder.append(" \nName: ").append(cartItem.getName())
                    .append("\nPrice: ")
                    .append(cartItem.getPrice())
                    .append("\nquantity: ")
                    .append(cartItem.getQuantity())
                    .append("\nProduct quantity: ")
                    .append(cartItem.getProductQuantity())
                    .append("\n");
            totalPrice+= cartItem.getQuantity()*cartItem.getPrice();
        }
        stringBuilder.append("\n Total price").append(totalPrice).append("\n");
        return stringBuilder.toString();
    }
}
