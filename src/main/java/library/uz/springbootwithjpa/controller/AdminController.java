package library.uz.springbootwithjpa.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import library.uz.springbootwithjpa.model.Admin;
import library.uz.springbootwithjpa.model.User;
import library.uz.springbootwithjpa.dto.request.AdminLoginDto;
import library.uz.springbootwithjpa.dto.request.CategoryCreateDto;
import library.uz.springbootwithjpa.service.impl.AdminServiceImpl;
import library.uz.springbootwithjpa.service.CategoryServise;
import library.uz.springbootwithjpa.service.UserServise;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminServiceImpl adminService;
    private final CategoryServise categoryServise;
    private final UserServise userServise;

    @GetMapping("home")
    public String home(Model model, HttpSession session){
        Admin admin = (Admin) session.getAttribute("admin");
        model.addAttribute("admin",admin);
        return "home";
    }


    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("admin" , new AdminLoginDto(null,null));
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute AdminLoginDto request, HttpSession session, Model model){
        Admin admin = adminService.login(request);
        if (admin !=null){
            session.setAttribute("admin", admin);
            return "redirect:/admin/home";
        }
        model.addAttribute("admin", new AdminLoginDto(null, null));
        return "login";
    }

    @GetMapping("/products")
    public String products(Model model,HttpSession session){

        Admin admin = (Admin)session.getAttribute("admin");
        model.addAttribute(
                "topCategories",
                categoryServise.getTopcategories()
        );

        model.addAttribute(
                "admin",
                admin
        );

        model.addAttribute(
                "category",
                new CategoryCreateDto(null,null)
        );

        return "products";
    }

    @GetMapping("orders")
    public String orders(Model model,HttpSession session) {
        Admin admin = (Admin)session.getAttribute("admin");
        model.addAttribute("admin", admin);
        return "orders";
    }


    @GetMapping("/users")
    public String users(Model model,HttpSession session){
        Admin admin = (Admin) session.getAttribute("admin");
        List<User> users = userServise.getAll();
        model.addAttribute("users" , users);
        model.addAttribute("admin",admin);
        return "home";
    }

    @GetMapping("/register")
    public String register1(Model model){
        model.addAttribute("admin",new AdminLoginDto(null,null));
        return "qqqq";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute AdminLoginDto req, Model model){
        System.out.println(req);
        adminService.register(req);
        model.addAttribute("admin" , new AdminLoginDto(null , null));
        return "login";
    }


}
