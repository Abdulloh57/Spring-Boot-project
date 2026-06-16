package library.uz.springbootwithjpa.service;

import library.uz.springbootwithjpa.dto.request.AdminLoginDto;
import library.uz.springbootwithjpa.model.Admin;

public interface AdminService {
    Admin login(AdminLoginDto request);

    void register(AdminLoginDto req);
}
