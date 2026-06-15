package library.uz.springbootwithjpa.TGbot.states;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDataState {
    UserState userState;
    Integer currentRecordId;
    int quantity=1;
    public UserDataState(UserState userState) {
        this.userState = userState;
    }
}
