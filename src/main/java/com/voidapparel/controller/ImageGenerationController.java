package com.voidapparel.controller;

import com.voidapparel.dto.ImageGenerationRequest;
import com.voidapparel.dto.ImageGenerationResponse;
import com.voidapparel.service.ImageGenerationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/designs")
@Tag(name = "Image Generation", description = "Generate preview images for custom designs")
public class ImageGenerationController {
    
    @Autowired
    private ImageGenerationService imageGenerationService;
    
    @PostMapping("/generate-preview")
    @Operation(summary = "Generate preview image for custom design",
               description = "Generates a preview image based on customization options using AI or placeholder images")
    public ResponseEntity<ImageGenerationResponse> generatePreview(
            @Valid @RequestBody ImageGenerationRequest request) {
        ImageGenerationResponse response = imageGenerationService.generatePreview(request);
        return ResponseEntity.ok(response);
    }
}
