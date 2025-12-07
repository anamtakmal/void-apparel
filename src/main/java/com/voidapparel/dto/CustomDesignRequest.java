package com.voidapparel.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomDesignRequest {
    
    @NotBlank(message = "Design name is required")
    private String name;
    
    @NotNull(message = "Product ID is required")
    private Long productId;
    
    @NotNull(message = "Configuration is required")
    private Map<String, String> configuration;
}
