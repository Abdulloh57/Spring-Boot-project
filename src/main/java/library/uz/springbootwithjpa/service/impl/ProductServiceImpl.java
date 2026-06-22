package library.uz.springbootwithjpa.service.impl;

import jakarta.transaction.Transactional;
import library.uz.springbootwithjpa.dao.OrderItemRepository;
import library.uz.springbootwithjpa.dao.ProductRepository;
import library.uz.springbootwithjpa.dto.request.ProductCreateDto;
import library.uz.springbootwithjpa.dto.response.ProductResponseDto;
import library.uz.springbootwithjpa.exception.RecordNotFoundException;
import library.uz.springbootwithjpa.model.Category;
import library.uz.springbootwithjpa.model.OrderItem;
import library.uz.springbootwithjpa.model.Product;
import library.uz.springbootwithjpa.service.CategoryService;
import library.uz.springbootwithjpa.service.ProductServise;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductServise {
    private final ProductRepository productRepository;
    private final CategoryService categoryServise;
    private final OrderItemRepository orderItemRepository;

    @Override
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

    @Override
    public List<ProductResponseDto> getProducts(int id) {
        Category category = categoryServise.getById(id);
        if (category == null) return null;
        return productRepository.findByCategory(category)
                .stream()
                .map(this::mapProduct).toList();
    }

    @Override
    public ProductResponseDto getProduct(int id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(this::mapProduct).orElseThrow(() -> new RecordNotFoundException("Product found",id));
    }

    @Override
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

    @Override
    public ProductResponseDto mapProduct(Product product){
        return new ProductResponseDto(product.getId(),
                product.getName(),
                product.getPrice(),
                product.getQuantity(),
                product.getImageUrl(),
                product.getCategory().getId());
    }

}
