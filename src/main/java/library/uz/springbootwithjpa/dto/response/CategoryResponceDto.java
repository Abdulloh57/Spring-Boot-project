package library.uz.springbootwithjpa.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class CategoryResponceDto {
    private int id;
    private String name;
    private int parentId;
}
