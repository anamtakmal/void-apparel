package com.voidapparel.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "order_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
    
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    
    @Column(nullable = false)
    private Integer quantity;
    
    @Column(nullable = false)
    private BigDecimal unitPrice;
    
    @ElementCollection
    @CollectionTable(name = "order_item_customizations", joinColumns = @JoinColumn(name = "order_item_id"))
    @MapKeyColumn(name = "customization_key")
    @Column(name = "customization_value")
    private Map<String, String> customizations = new HashMap<>();
    
    @Column(nullable = false)
    private BigDecimal customizationPrice = BigDecimal.ZERO;
    
    @Column(nullable = false)
    private BigDecimal totalPrice;
}
