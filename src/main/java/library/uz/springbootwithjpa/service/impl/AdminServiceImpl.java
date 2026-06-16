package library.uz.springbootwithjpa.service.impl;


import library.uz.springbootwithjpa.dao.AdminRepository;

import library.uz.springbootwithjpa.model.Admin;
import library.uz.springbootwithjpa.dto.request.AdminLoginDto;
import library.uz.springbootwithjpa.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository repository;

    @Override
    public Admin login(AdminLoginDto request){
         return repository.findByUsernameAndPassword(request.username(), request.password());
    }
    @Override
    public void register(AdminLoginDto req) {
        Admin admin = new Admin();
        admin.setUsername(req.username());
        admin.setPassword(req.password());
        repository.save(admin);
    }
}
