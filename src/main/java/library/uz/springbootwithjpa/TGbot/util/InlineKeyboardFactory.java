package library.uz.springbootwithjpa.TGbot.util;

import library.uz.springbootwithjpa.model.Product;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class InlineKeyboardFactory {
    public static InlineKeyboardMarkup makeProduct(Product product, int quantity){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        inlineKeyboardMarkup.setKeyboard(rows);

        rows.add(
                BotUtil.getRow(new String[][]{
                        {"-", "dec"+product.getId()}, {String.valueOf(quantity), "qty"+product.getId()}, {"+", "inc"+product.getId()}})
        );
        rows.add(BotUtil.getRow(new String[][]{
                {"Add Cart", "ADC" + product.getId()},
                {"Back", "BCK" + product.getCategory().getId()},
                {"Bosh Menu", "NXT"}
        }));
        return inlineKeyboardMarkup;
    }
}
