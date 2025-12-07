package com.voidapparel.service;

import com.voidapparel.dto.OrderRequest;
import com.voidapparel.exception.BadRequestException;
import com.voidapparel.exception.ResourceNotFoundException;
import com.voidapparel.model.*;
import com.voidapparel.repository.CartRepository;
import com.voidapparel.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private CartService cartService;
    
    @Autowired
    private CartRepository cartRepository;
    
    @Transactional
    public Order createOrder(User user, OrderRequest request) {
        Cart cart = cartService.getOrCreateCart(user);
        
        if (cart.getItems().isEmpty()) {
            throw new BadRequestException("Cannot create order with empty cart");
        }
        
        Order order = new Order();
        order.setUser(user);
        order.setSubtotal(cart.getSubtotal());
        order.setDiscount(cart.getDiscount());
        order.setTax(cart.getTax());
        order.setTotal(cart.getTotal());
        
        if (cart.getPromoCode() != null) {
            order.setPromoCodeUsed(cart.getPromoCode().getCode());
        }
        
        // Convert cart items to order items
        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setUnitPrice(cartItem.getUnitPrice());
            orderItem.setCustomizations(cartItem.getCustomizations());
            orderItem.setCustomizationPrice(cartItem.getCustomizationPrice());
            orderItem.setTotalPrice(cartItem.getTotalPrice());
            order.getItems().add(orderItem);
        }
        
        order = orderRepository.save(order);
        
        // Clear cart after order
        cart.getItems().clear();
        cart.setPromoCode(null);
        cartRepository.save(cart);
        
        return order;
    }
    
    public List<Order> getUserOrders(User user) {
        return orderRepository.findByUserOrderByCreatedAtDesc(user);
    }
    
    public Order getOrderById(User user, Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));
        
        if (!order.getUser().getId().equals(user.getId())) {
            throw new BadRequestException("You don't have permission to view this order");
        }
        
        return order;
    }
}
