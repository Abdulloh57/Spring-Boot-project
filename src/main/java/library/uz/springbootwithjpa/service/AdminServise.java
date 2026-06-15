package library.uz.springbootwithjpa.service;


import library.uz.springbootwithjpa.dao.AdminRepository;

import library.uz.springbootwithjpa.model.Admin;
import library.uz.springbootwithjpa.model.dto.AdminLoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServise {
    private final AdminRepository repository;

    public Admin login(AdminLoginDto request){
         return repository.findByUsernameAndPassword(request.username(), request.password());
    }

    public void register(AdminLoginDto req) {
        Admin admin = new Admin();
        admin.setUsername(req.username());
        admin.setPassword(req.password());
        repository.save(admin);
    }
}
