package library.uz.springbootwithjpa.controller;

import jakarta.servlet.http.HttpSession;

import library.uz.springbootwithjpa.model.dto.AdminLoginDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String home(Model model){
        model.addAttribute("admin", new AdminLoginDto(null, null));
        return "login";
    }

    @PostMapping("logout")
    public String logout(Model model, HttpSession session){
        session.invalidate();
        model.addAttribute("admin", new AdminLoginDto(null, null));
        return "login";
    }
}
