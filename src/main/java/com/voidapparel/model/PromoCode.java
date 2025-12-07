package com.voidapparel.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "promo_codes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromoCode {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String code;
    
    @Column(nullable = false)
    private BigDecimal discountPercentage;
    
    @Column(nullable = false)
    private boolean active = true;
    
    private LocalDateTime expiresAt;
    
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
