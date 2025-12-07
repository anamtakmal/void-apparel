package com.voidapparel.service;

import com.voidapparel.dto.CartItemRequest;
import com.voidapparel.dto.CartResponse;
import com.voidapparel.exception.BadRequestException;
import com.voidapparel.exception.ResourceNotFoundException;
import com.voidapparel.model.*;
import com.voidapparel.repository.CartRepository;
import com.voidapparel.repository.PromoCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {
    
    @Autowired
    private CartRepository cartRepository;
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private PromoCodeRepository promoCodeRepository;
    
    public Cart getOrCreateCart(User user) {
        return cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setUser(user);
                    return cartRepository.save(cart);
                });
    }
    
    @Transactional
    public CartResponse addItemToCart(User user, CartItemRequest request) {
        Cart cart = getOrCreateCart(user);
        Product product = productService.getProductById(request.getProductId());
        
        // Check if item with same customizations already exists
        CartItem existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()) &&
                               item.getCustomizations().equals(request.getCustomizations() != null ? 
                                       request.getCustomizations() : new java.util.HashMap<>()))
                .findFirst()
                .orElse(null);
        
        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + request.getQuantity());
        } else {
            CartItem newItem = new CartItem();
            newItem.setProduct(product);
            newItem.setQuantity(request.getQuantity());
            newItem.setUnitPrice(product.getBasePrice());
            
            if (request.getCustomizations() != null) {
                newItem.setCustomizations(request.getCustomizations());
                // Calculate customization price based on number of customizations
                BigDecimal customizationPrice = BigDecimal.valueOf(request.getCustomizations().size() * 10);
                newItem.setCustomizationPrice(customizationPrice);
            }
            
            cart.addItem(newItem);
        }
        
        cart = cartRepository.save(cart);
        return convertToResponse(cart);
    }
    
    @Transactional
    public CartResponse updateCartItem(User user, Long itemId, Integer quantity) {
        Cart cart = getOrCreateCart(user);
        
        CartItem item = cart.getItems().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));
        
        if (quantity <= 0) {
            cart.removeItem(item);
        } else {
            item.setQuantity(quantity);
        }
        
        cart = cartRepository.save(cart);
        return convertToResponse(cart);
    }
    
    @Transactional
    public CartResponse removeCartItem(User user, Long itemId) {
        Cart cart = getOrCreateCart(user);
        
        CartItem item = cart.getItems().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));
        
        cart.removeItem(item);
        cart = cartRepository.save(cart);
        return convertToResponse(cart);
    }
    
    public CartResponse getCart(User user) {
        Cart cart = getOrCreateCart(user);
        return convertToResponse(cart);
    }
    
    @Transactional
    public CartResponse applyPromoCode(User user, String code) {
        Cart cart = getOrCreateCart(user);
        
        PromoCode promoCode = promoCodeRepository.findByCodeAndActiveTrue(code)
                .orElseThrow(() -> new BadRequestException("Invalid or expired promo code"));
        
        cart.setPromoCode(promoCode);
        cart = cartRepository.save(cart);
        return convertToResponse(cart);
    }
    
    @Transactional
    public CartResponse removePromoCode(User user) {
        Cart cart = getOrCreateCart(user);
        cart.setPromoCode(null);
        cart = cartRepository.save(cart);
        return convertToResponse(cart);
    }
    
    private CartResponse convertToResponse(Cart cart) {
        List<CartResponse.CartItemResponse> itemResponses = cart.getItems().stream()
                .map(item -> new CartResponse.CartItemResponse(
                        item.getId(),
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getUnitPrice(),
                        item.getCustomizationPrice(),
                        item.getTotalPrice(),
                        item.getCustomizations()
                ))
                .collect(Collectors.toList());
        
        return new CartResponse(
                cart.getId(),
                itemResponses,
                cart.getSubtotal(),
                cart.getDiscount(),
                cart.getTax(),
                cart.getTotal(),
                cart.getPromoCode() != null ? cart.getPromoCode().getCode() : null
        );
    }
}
