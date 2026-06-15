package library.uz.springbootwithjpa.service;


import jakarta.transaction.Transactional;
import library.uz.springbootwithjpa.dao.OrderItemRepository;
import library.uz.springbootwithjpa.dao.ProductRepository;
import library.uz.springbootwithjpa.model.Category;
import library.uz.springbootwithjpa.model.OrderItem;
import library.uz.springbootwithjpa.model.Product;
import library.uz.springbootwithjpa.model.dto.ProductCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServise {
    private final ProductRepository productRepository;
    private final CategoryServise categoryServise;
    private final OrderItemRepository orderItemRepository;

    public void addProduct(ProductCreateDto req , String imageUrl){
        Product product = new Product();
        product.setName(req.name());
        product.setImageUrl(imageUrl);
        product.setPrice(req.price());
        product.setQuantity(req.quantity());
        Category byId = categoryServise.getById(req.categoryId());
        if (byId != null){
        product.setCategory(byId);
        }else return;
        productRepository.save(product);
    }

    public List<Product> getProducts(int id) {
        Category category = categoryServise.getById(id);
        if (category == null) return null;
        return productRepository.findByCategory(category);
    }

    public Product getProduct(int id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElse(null);
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



}
