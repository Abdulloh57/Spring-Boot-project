package library.uz.springbootwithjpa.service;


import jakarta.transaction.Transactional;
import library.uz.springbootwithjpa.dao.OrderItemRepository;
import library.uz.springbootwithjpa.dao.ProductRepository;
import library.uz.springbootwithjpa.dto.response.ProductResponseDto;
import library.uz.springbootwithjpa.exception.RecordNotFoundException;
import library.uz.springbootwithjpa.model.Category;
import library.uz.springbootwithjpa.model.OrderItem;
import library.uz.springbootwithjpa.model.Product;
import library.uz.springbootwithjpa.dto.request.ProductCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServise {
    private final ProductRepository productRepository;
    private final CategoryService categoryServise;
    private final OrderItemRepository orderItemRepository;

    public ProductResponseDto addProduct(ProductCreateDto req , String imageUrl){
        Product product = new Product();
        product.setName(req.name());
        product.setImageUrl(imageUrl);
        product.setPrice(req.price());
        product.setQuantity(req.quantity());
        Category byId = categoryServise.getById(req.categoryId());
        if (byId != null){
        product.setCategory(byId);
        }else throw new RecordNotFoundException("Category not found", req.categoryId());
        productRepository.save(product);
        return mapProduct(product);
    }

    public List<ProductResponseDto> getProducts(int id) {
        Category category = categoryServise.getById(id);
        if (category == null) return null;
        return productRepository.findByCategory(category)
                .stream()
                .map(this::mapProduct).toList();
    }

    public ProductResponseDto getProduct(int id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(this::mapProduct).orElseThrow(() -> new RecordNotFoundException("Product found",id));
    }


    @Transactional
    public void changStock(int orderId,boolean decrease){
        List<OrderItem> orderItems = orderItemRepository.findByOrder_Id(orderId);
        for (OrderItem item : orderItems){
            Product product = item.getProduct();
            if (decrease){
                product.setQuantity(product.getQuantity() - item.getQuantity());
            }
            else
                product.setQuantity(product.getQuantity() + item.getQuantity());
        }
    }

    public ProductResponseDto mapProduct(Product product){
        return new ProductResponseDto(product.getId(),
                product.getName(),
                product.getPrice(),
                product.getQuantity(),
                product.getImageUrl(),
                product.getCategory().getId());
    }



}
