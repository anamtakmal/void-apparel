package com.voidapparel.service;

import com.voidapparel.exception.ResourceNotFoundException;
import com.voidapparel.model.Product;
import com.voidapparel.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    
    public List<Product> getAllProducts() {
        return productRepository.findByActiveTrue();
    }
    
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }
    
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryAndActiveTrue(category);
    }
    
    public Map<String, Object> getCustomizationOptions(String category) {
        Map<String, Object> options = new HashMap<>();
        
        switch (category.toLowerCase()) {
            case "jeans":
                options.put("fits", List.of("Bootcut", "Cargo", "Straight", "Slim", "Baggy"));
                options.put("fabrics", List.of("Denim (Classic)", "Stretch Denim", "Rigid Denim", "Selvage Denim", "Corduroy", "Twill"));
                options.put("customizations", List.of("Patches", "Embroidery", "Rips"));
                options.put("colors", List.of("#222222", "#0066cc", "#000000", "#808080", "#ffffff"));
                break;
                
            case "winter":
                options.put("styles", List.of("Hoodie", "Jacket", "Long Coat", "Sweater"));
                options.put("fabrics", List.of("Wool", "Fleece", "Insulated Shell", "Leather", "Denim", "Corduroy"));
                options.put("customizations", List.of("Embroidery", "Extra Zipper", "Patches", "Removable Lining"));
                options.put("colors", List.of("#303030", "#000000", "#8b4513", "#2f4f4f", "#ffffff"));
                break;
                
            case "accessories":
                options.put("types", List.of("Bag", "Wallet"));
                options.put("bagTypes", List.of("Tote", "Backpack", "Messenger", "Clutch", "Crossbody"));
                options.put("customizations", List.of("Metal pieces", "Studs", "Stones", "Patches", "Embroidery"));
                options.put("colors", List.of("#222222", "#8b4513", "#000000", "#808080", "#ffffff"));
                break;
                
            default:
                throw new ResourceNotFoundException("Category not found: " + category);
        }
        
        return options;
    }
}
