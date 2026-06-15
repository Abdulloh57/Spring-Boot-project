package library.uz.springbootwithjpa.service;


import jakarta.transaction.Transactional;
import library.uz.springbootwithjpa.dao.UserRepository;
import library.uz.springbootwithjpa.exception.RecordNotFoundException;
import library.uz.springbootwithjpa.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Contact;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServise {
    private final UserRepository userRepository;

    @Transactional
    public void firstRegistration(Long chatId){
        User user = userRepository.findByChatId(chatId);
        if (user == null) {
            user = new User();
            user.setChatId(chatId);
            User save = userRepository.save(user);
            if (user !=null){
                System.out.println(user);
            }
        }
    }

    public User getUser(Long chatId){
        return userRepository.findByChatId(chatId);
    }

    @Transactional
    //Bu joyda transactional nima uchun kerak agar biror bir field ni o'zgartirayotganda xatolik chiqsa rollbeck bo'lishi uchun
    public void finalRegistration(Long chatId, Contact contact) {
        User user = userRepository.findByChatId(chatId);
        if (user == null){
            throw new RecordNotFoundException("user not found "+chatId);
        }
        user.setFirstname(contact.getFirstName());
        user.setTgId(contact.getUserId());
        user.setPhoneNumber(contact.getPhoneNumber());
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }
}
