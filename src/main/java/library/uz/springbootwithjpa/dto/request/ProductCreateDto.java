package library.uz.springbootwithjpa.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ProductCreateDto(
        @NotBlank @Size(min = 4)
        String name,
        @Positive
        Double price,
        @Positive
        Integer quantity,
        @Positive
        Integer categoryId) {

}
