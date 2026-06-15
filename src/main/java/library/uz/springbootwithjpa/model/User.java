package library.uz.springbootwithjpa.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //Bu tizim identificatori
    private int id;
    private Long tgId;
    //Bu tashqi ilova identificatori
    private Long chatId;
    private String firstname;
    private String phoneNumber;

    public User(int id) {
        this.id = id;
    }
}
