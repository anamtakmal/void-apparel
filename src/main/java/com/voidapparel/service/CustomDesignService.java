package com.voidapparel.service;

import com.voidapparel.dto.CustomDesignRequest;
import com.voidapparel.exception.BadRequestException;
import com.voidapparel.exception.ResourceNotFoundException;
import com.voidapparel.model.CustomDesign;
import com.voidapparel.model.Product;
import com.voidapparel.model.User;
import com.voidapparel.repository.CustomDesignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomDesignService {
    
    @Autowired
    private CustomDesignRepository customDesignRepository;
    
    @Autowired
    private ProductService productService;
    
    @Transactional
    public CustomDesign saveDesign(User user, CustomDesignRequest request) {
        Product product = productService.getProductById(request.getProductId());
        
        CustomDesign design = new CustomDesign();
        design.setUser(user);
        design.setProduct(product);
        design.setName(request.getName());
        design.setConfiguration(request.getConfiguration());
        
        return customDesignRepository.save(design);
    }
    
    @Transactional
    public CustomDesign updateDesign(User user, Long designId, CustomDesignRequest request) {
        CustomDesign design = customDesignRepository.findByIdAndUserId(designId, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Design not found"));
        
        if (request.getProductId() != null) {
            Product product = productService.getProductById(request.getProductId());
            design.setProduct(product);
        }
        
        if (request.getName() != null) {
            design.setName(request.getName());
        }
        
        if (request.getConfiguration() != null) {
            design.setConfiguration(request.getConfiguration());
        }
        
        return customDesignRepository.save(design);
    }
    
    public List<CustomDesign> getUserDesigns(User user) {
        return customDesignRepository.findByUserOrderByCreatedAtDesc(user);
    }
    
    public CustomDesign getDesignById(User user, Long designId) {
        CustomDesign design = customDesignRepository.findById(designId)
                .orElseThrow(() -> new ResourceNotFoundException("Design not found"));
        
        if (!design.getUser().getId().equals(user.getId())) {
            throw new BadRequestException("You don't have permission to view this design");
        }
        
        return design;
    }
    
    @Transactional
    public void deleteDesign(User user, Long designId) {
        CustomDesign design = customDesignRepository.findByIdAndUserId(designId, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Design not found"));
        
        customDesignRepository.delete(design);
    }
}
