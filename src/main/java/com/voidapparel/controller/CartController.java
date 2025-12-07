package com.voidapparel.controller;

import com.voidapparel.dto.CartItemRequest;
import com.voidapparel.dto.CartResponse;
import com.voidapparel.dto.PromoCodeRequest;
import com.voidapparel.model.User;
import com.voidapparel.service.CartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    
    @Autowired
    private CartService cartService;
    
    @GetMapping
    public ResponseEntity<CartResponse> getCart(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(cartService.getCart(user));
    }
    
    @PostMapping("/items")
    public ResponseEntity<CartResponse> addItemToCart(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody CartItemRequest request) {
        return ResponseEntity.ok(cartService.addItemToCart(user, request));
    }
    
    @PutMapping("/items/{itemId}")
    public ResponseEntity<CartResponse> updateCartItem(
            @AuthenticationPrincipal User user,
            @PathVariable Long itemId,
            @RequestBody Map<String, Integer> request) {
        Integer quantity = request.get("quantity");
        return ResponseEntity.ok(cartService.updateCartItem(user, itemId, quantity));
    }
    
    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<CartResponse> removeCartItem(
            @AuthenticationPrincipal User user,
            @PathVariable Long itemId) {
        return ResponseEntity.ok(cartService.removeCartItem(user, itemId));
    }
    
    @PostMapping("/promo")
    public ResponseEntity<CartResponse> applyPromoCode(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody PromoCodeRequest request) {
        return ResponseEntity.ok(cartService.applyPromoCode(user, request.getCode()));
    }
    
    @DeleteMapping("/promo")
    public ResponseEntity<CartResponse> removePromoCode(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(cartService.removePromoCode(user));
    }
}
