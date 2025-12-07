package com.voidapparel.config;

import com.voidapparel.model.Product;
import com.voidapparel.model.PromoCode;
import com.voidapparel.repository.ProductRepository;
import com.voidapparel.repository.PromoCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private PromoCodeRepository promoCodeRepository;
    
    @Override
    public void run(String... args) {
        initializeProducts();
        initializePromoCodes();
    }
    
    private void initializeProducts() {
        // Jeans products
        Product jeans1 = new Product();
        jeans1.setName("Classic Bootcut Jeans");
        jeans1.setDescription("Premium denim jeans with customizable options");
        jeans1.setCategory("jeans");
        jeans1.setBasePrice(new BigDecimal("149.99"));
        jeans1.setFitOptions(Arrays.asList("Bootcut", "Cargo", "Straight", "Slim", "Baggy"));
        jeans1.setFabricTypes(Arrays.asList("Denim (Classic)", "Stretch Denim", "Rigid Denim", "Selvage Denim", "Corduroy", "Twill"));
        jeans1.setColors(Arrays.asList("#222222", "#0066cc", "#000000", "#808080"));
        jeans1.setCustomizable(true);
        jeans1.setActive(true);
        
        Product jeans2 = new Product();
        jeans2.setName("Slim Fit Jeans");
        jeans2.setDescription("Modern slim fit jeans with stretch");
        jeans2.setCategory("jeans");
        jeans2.setBasePrice(new BigDecimal("139.99"));
        jeans2.setFitOptions(Arrays.asList("Slim", "Straight"));
        jeans2.setFabricTypes(Arrays.asList("Stretch Denim", "Denim (Classic)"));
        jeans2.setColors(Arrays.asList("#000000", "#0066cc", "#808080"));
        jeans2.setCustomizable(true);
        jeans2.setActive(true);
        
        // Winter products
        Product winter1 = new Product();
        winter1.setName("Premium Hoodie");
        winter1.setDescription("Cozy hoodie with custom options");
        winter1.setCategory("winter");
        winter1.setBasePrice(new BigDecimal("89.99"));
        winter1.setStyleOptions(Arrays.asList("Hoodie", "Jacket", "Long Coat", "Sweater"));
        winter1.setFabricTypes(Arrays.asList("Wool", "Fleece", "Insulated Shell", "Leather", "Denim", "Corduroy"));
        winter1.setColors(Arrays.asList("#303030", "#000000", "#8b4513", "#2f4f4f"));
        winter1.setCustomizable(true);
        winter1.setActive(true);
        
        Product winter2 = new Product();
        winter2.setName("Leather Jacket");
        winter2.setDescription("Genuine leather jacket with premium finish");
        winter2.setCategory("winter");
        winter2.setBasePrice(new BigDecimal("299.99"));
        winter2.setStyleOptions(Arrays.asList("Jacket"));
        winter2.setFabricTypes(Arrays.asList("Leather", "Denim"));
        winter2.setColors(Arrays.asList("#000000", "#8b4513"));
        winter2.setCustomizable(true);
        winter2.setActive(true);
        
        // Accessories products
        Product accessory1 = new Product();
        accessory1.setName("Leather Tote Bag");
        accessory1.setDescription("Spacious tote bag with custom details");
        accessory1.setCategory("accessories");
        accessory1.setBasePrice(new BigDecimal("79.99"));
        accessory1.setAccessoryTypes(Arrays.asList("Bag"));
        accessory1.setBagTypes(Arrays.asList("Tote", "Backpack", "Messenger", "Clutch", "Crossbody"));
        accessory1.setColors(Arrays.asList("#222222", "#8b4513", "#000000", "#808080"));
        accessory1.setCustomizable(true);
        accessory1.setActive(true);
        
        Product accessory2 = new Product();
        accessory2.setName("Designer Wallet");
        accessory2.setDescription("Compact wallet with premium materials");
        accessory2.setCategory("accessories");
        accessory2.setBasePrice(new BigDecimal("49.99"));
        accessory2.setAccessoryTypes(Arrays.asList("Wallet"));
        accessory2.setColors(Arrays.asList("#222222", "#8b4513", "#000000"));
        accessory2.setCustomizable(true);
        accessory2.setActive(true);
        
        productRepository.saveAll(Arrays.asList(jeans1, jeans2, winter1, winter2, accessory1, accessory2));
    }
    
    private void initializePromoCodes() {
        PromoCode void20 = new PromoCode();
        void20.setCode("VOID20");
        void20.setDiscountPercentage(new BigDecimal("20"));
        void20.setActive(true);
        
        PromoCode welcome10 = new PromoCode();
        welcome10.setCode("WELCOME10");
        welcome10.setDiscountPercentage(new BigDecimal("10"));
        welcome10.setActive(true);
        
        promoCodeRepository.saveAll(Arrays.asList(void20, welcome10));
    }
}
