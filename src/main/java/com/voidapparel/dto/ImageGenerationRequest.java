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
public class ImageGenerationRequest {
    
    @NotBlank(message = "Category is required")
    private String category;
    
    @NotNull(message = "Options are required")
    private Map<String, Object> options;
}
