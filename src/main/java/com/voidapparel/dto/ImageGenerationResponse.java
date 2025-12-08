package com.voidapparel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageGenerationResponse {
    
    private String imageUrl;
    private String prompt;
}
