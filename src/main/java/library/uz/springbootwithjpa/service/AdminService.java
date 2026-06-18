package library.uz.springbootwithjpa.service;

import jakarta.servlet.http.HttpSession;
import library.uz.springbootwithjpa.dto.request.AdminLoginDto;
import library.uz.springbootwithjpa.dto.response.AdminResponseDto;
import library.uz.springbootwithjpa.model.Admin;

public interface AdminService {

    AdminResponseDto login(AdminLoginDto request,  HttpSession session);

    AdminResponseDto register(AdminLoginDto req);
}
