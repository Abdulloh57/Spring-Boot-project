package library.uz.springbootwithjpa.service.impl;


import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import library.uz.springbootwithjpa.dao.AdminRepository;

import library.uz.springbootwithjpa.dto.response.AdminResponseDto;
import library.uz.springbootwithjpa.exception.AlreadyExistException;
import library.uz.springbootwithjpa.exception.RecordNotFoundException;
import library.uz.springbootwithjpa.model.Admin;
import library.uz.springbootwithjpa.dto.request.AdminLoginDto;
import library.uz.springbootwithjpa.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository repository;

    @Override
    public AdminResponseDto login(AdminLoginDto request, HttpSession session){
        Admin admin = repository.findByUsernameAndPassword(request.username(), request.password());
        if (admin == null){
            throw new RecordNotFoundException("Admin not found");
        }
        session.setAttribute("admin" ,admin);
        return new AdminResponseDto(admin.getId(), admin.getUsername());
    }

    @Override
    @Transactional
    public AdminResponseDto register(AdminLoginDto req) {
        Admin admin = repository.findByUsername(req.username());
        if (admin !=null){
            throw new AlreadyExistException("Username already exist with "+req.username());
        }
        admin = new Admin();
        admin.setUsername(req.username());
        admin.setPassword(req.password());
        admin = repository.save(admin);
        return new AdminResponseDto(admin.getId(), admin.getUsername());
    }
}
