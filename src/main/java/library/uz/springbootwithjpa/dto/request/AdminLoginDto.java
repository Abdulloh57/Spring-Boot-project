package library.uz.springbootwithjpa.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AdminLoginDto(
        @Size(min = 4, max = 15) String username,
        @NotBlank(message = "password bo'sh bo'lishi mumkin emas ") String  password) {
}
