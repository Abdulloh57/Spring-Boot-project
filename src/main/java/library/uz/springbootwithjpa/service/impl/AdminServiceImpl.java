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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository repository;
    private final PasswordEncoder passwordEncoder;

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
        Optional<Admin> admin = repository.findByUsername(req.username());
        if (admin.isPresent()){
            throw new AlreadyExistException("admin found with username : "+req.username());
        }
        Admin adminEntity = new Admin();
        adminEntity.setUsername(req.username());
        adminEntity.setPassword(passwordEncoder.encode(req.password()));
        Admin saved = repository.save(adminEntity);
        return new AdminResponseDto(saved.getId(), saved.getUsername());
    }
}
