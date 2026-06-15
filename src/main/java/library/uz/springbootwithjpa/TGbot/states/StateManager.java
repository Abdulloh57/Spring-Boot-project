package library.uz.springbootwithjpa.TGbot.states;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
@Service /// nega buni qilmadima axir boshqaruvni springga topshirish kerakku
public class StateManager {
    public final ConcurrentHashMap<Long, UserDataState> chatIdToUserState = new ConcurrentHashMap<>();

    public void updateState(Long chatId,UserState state, int id){
        UserDataState userDataState = chatIdToUserState.get(chatId);
        /// Bu joyida race condition muammosini atomar darajada boshqarish kerak ekan bo'lmasa hatolik bo'ladi
        userDataState.setUserState(state);
        userDataState.setCurrentRecordId(id);
    }



    public UserDataState getState(Long chatId){
        return chatIdToUserState.get(chatId);
    }

    public void createState(Long chatId, UserDataState userDataState) {
        chatIdToUserState.put(chatId,userDataState);
    }
}
