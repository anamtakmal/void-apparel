package com.voidapparel.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(length = 1000)
    private String description;
    
    @Column(nullable = false)
    private String category; // "jeans", "winter", "accessories"
    
    @Column(nullable = false)
    private BigDecimal basePrice;
    
    private String imageUrl;
    
    // For jeans
    @ElementCollection
    @CollectionTable(name = "product_fit_options", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "fit_option")
    private List<String> fitOptions; // Bootcut, Cargo, Straight, Slim, Baggy
    
    // For winter
    @ElementCollection
    @CollectionTable(name = "product_style_options", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "style_option")
    private List<String> styleOptions; // Hoodie, Jacket, Long Coat, Sweater
    
    // For accessories
    @ElementCollection
    @CollectionTable(name = "product_accessory_types", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "accessory_type")
    private List<String> accessoryTypes; // Bag, Wallet
    
    @ElementCollection
    @CollectionTable(name = "product_bag_types", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "bag_type")
    private List<String> bagTypes; // Tote, Backpack, Messenger, Clutch, Crossbody
    
    @ElementCollection
    @CollectionTable(name = "product_fabric_types", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "fabric_type")
    private List<String> fabricTypes;
    
    @ElementCollection
    @CollectionTable(name = "product_colors", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "color")
    private List<String> colors;
    
    @Column(nullable = false)
    private boolean customizable = true;
    
    @Column(nullable = false)
    private boolean active = true;
}
