package library.uz.springbootwithjpa.service;

import library.uz.springbootwithjpa.model.User;
import org.telegram.telegrambots.meta.api.objects.Contact;
import java.util.List;

public interface UserServise {

    void firstRegistration(Long chatId);

    User getUser(Long chatId);

    void finalRegistration(Long chatId, Contact contact);

    List<User> getAll();
}
