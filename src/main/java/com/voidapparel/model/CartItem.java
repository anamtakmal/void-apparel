package com.voidapparel.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "cart_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;
    
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    
    @Column(nullable = false)
    private Integer quantity = 1;
    
    @Column(nullable = false)
    private BigDecimal unitPrice;
    
    // Customization details stored as JSON-like map
    @ElementCollection
    @CollectionTable(name = "cart_item_customizations", joinColumns = @JoinColumn(name = "cart_item_id"))
    @MapKeyColumn(name = "customization_key")
    @Column(name = "customization_value")
    private Map<String, String> customizations = new HashMap<>();
    
    @Column(nullable = false)
    private BigDecimal customizationPrice = BigDecimal.ZERO;
    
    public BigDecimal getTotalPrice() {
        return unitPrice.add(customizationPrice).multiply(BigDecimal.valueOf(quantity));
    }
}
