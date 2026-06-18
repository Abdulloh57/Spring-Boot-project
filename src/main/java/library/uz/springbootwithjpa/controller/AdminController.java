package library.uz.springbootwithjpa.controller;

import jakarta.servlet.http.HttpSession;
import library.uz.springbootwithjpa.dto.request.AdminLoginDto;
import library.uz.springbootwithjpa.dto.response.AdminResponseDto;
import library.uz.springbootwithjpa.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
    //ishladi
    @PostMapping("/login")
    public AdminResponseDto login(@RequestBody AdminLoginDto req, HttpSession session){
        return adminService.login(req,session);
    }
    //ishladi
    @PostMapping("/register")
    public ResponseEntity<AdminResponseDto> register(@RequestBody AdminLoginDto req){
        return ResponseEntity.ok(adminService.register(req));
    }
}
