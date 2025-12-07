package com.voidapparel.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
    
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();
    
    @ManyToOne
    @JoinColumn(name = "promo_code_id")
    private PromoCode promoCode;
    
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    private LocalDateTime updatedAt;
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    public BigDecimal getSubtotal() {
        return items.stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    public BigDecimal getDiscount() {
        if (promoCode == null) {
            return BigDecimal.ZERO;
        }
        return getSubtotal().multiply(promoCode.getDiscountPercentage()
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));
    }
    
    public BigDecimal getTax() {
        BigDecimal taxableAmount = getSubtotal().subtract(getDiscount());
        return taxableAmount.multiply(BigDecimal.valueOf(0.08)).setScale(2, RoundingMode.HALF_UP); // 8% tax
    }
    
    public BigDecimal getTotal() {
        return getSubtotal().subtract(getDiscount()).add(getTax());
    }
    
    public void addItem(CartItem item) {
        items.add(item);
        item.setCart(this);
    }
    
    public void removeItem(CartItem item) {
        items.remove(item);
        item.setCart(null);
    }
}
