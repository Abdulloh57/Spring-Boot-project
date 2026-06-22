package library.uz.springbootwithjpa.service.impl;

import jakarta.transaction.Transactional;
import library.uz.springbootwithjpa.dao.UserRepository;
import library.uz.springbootwithjpa.exception.RecordNotFoundException;
import library.uz.springbootwithjpa.model.User;
import library.uz.springbootwithjpa.service.UserServise;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Contact;

import java.util.List;
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserServise {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void firstRegistration(Long chatId){
        User user = userRepository.findByChatId(chatId);
        if (user == null) {
            user = new User();
            user.setChatId(chatId);
            userRepository.save(user);
        }
    }

    @Override
    public User getUser(Long chatId){
        return userRepository.findByChatId(chatId);
    }

    @Override
    @Transactional
    public void finalRegistration(Long chatId, Contact contact) {
        User user = userRepository.findByChatId(chatId);
        if (user == null){
            throw new RecordNotFoundException("user not found "+chatId);
        }
        user.setFirstname(contact.getFirstName());
        user.setTgId(contact.getUserId());
        user.setPhoneNumber(contact.getPhoneNumber());
    }

    @Override
    public List<User> getAll(){
        return userRepository.findAll();
    }

}
