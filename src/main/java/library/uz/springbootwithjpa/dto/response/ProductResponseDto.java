package library.uz.springbootwithjpa.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Builder
public class ProductResponseDto {
    private int id;
    private String name;
    private double price;
    private int quantity;
    private String imageUrl;
    private int categoryId;
}
