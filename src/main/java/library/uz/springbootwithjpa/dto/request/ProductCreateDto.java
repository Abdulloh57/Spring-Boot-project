package library.uz.springbootwithjpa.dto.request;

public record ProductCreateDto(String name,double price, int quantity, int categoryId) {

}
