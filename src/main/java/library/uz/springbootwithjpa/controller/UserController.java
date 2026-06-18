package library.uz.springbootwithjpa.controller;

import library.uz.springbootwithjpa.model.User;
import library.uz.springbootwithjpa.service.UserServise;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class UserController {
    private final UserServise userServise;

    @GetMapping
    public List<User> getUsers(){
        return userServise.getAll();
    }
}
