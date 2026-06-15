package library.uz.springbootwithjpa.model.dto;

public record ProductCreateDto(String name,double price, int quantity, int categoryId) {

}
