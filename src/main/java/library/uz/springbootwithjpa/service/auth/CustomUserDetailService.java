package library.uz.springbootwithjpa.service.auth;

import library.uz.springbootwithjpa.dao.AdminRepository;
import library.uz.springbootwithjpa.exception.RecordNotFoundException;
import library.uz.springbootwithjpa.model.Admin;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
   private final AdminRepository adminRepository;
    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByUsername(username).orElseThrow(
                () ->  new RecordNotFoundException("admin not found with username: "+username)
        );
         return User.withUsername(admin.getUsername())
                .password(admin.getPassword())
                .authorities(Collections.emptyList()) // yoki .roles("ADMIN")
                .build();
    }
}
