package com.voidapparel.service;

import com.voidapparel.dto.ImageGenerationRequest;
import com.voidapparel.dto.ImageGenerationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ImageGenerationService {
    
    private static final Logger logger = LoggerFactory.getLogger(ImageGenerationService.class);
    
    @Value("${app.image-generation.mode:placeholder}")
    private String imageGenerationMode;
    
    /**
     * Generate an image preview based on customization options
     * Currently uses placeholder mode - returns static images based on category
     * Can be extended to use OpenAI DALL-E or other AI image generation services
     */
    public ImageGenerationResponse generatePreview(ImageGenerationRequest request) {
        logger.info("Generating preview for category: {} with mode: {}", request.getCategory(), imageGenerationMode);
        
        String prompt = buildPrompt(request.getCategory(), request.getOptions());
        String imageUrl = getPlaceholderImage(request.getCategory());
        
        logger.debug("Generated prompt: {}", prompt);
        logger.debug("Image URL: {}", imageUrl);
        
        return new ImageGenerationResponse(imageUrl, prompt);
    }
    
    /**
     * Build a descriptive prompt from customization options
     * Note: Color values are passed as hex codes for placeholder mode
     * In a real AI implementation, consider converting hex to descriptive names
     */
    private String buildPrompt(String category, Map<String, Object> options) {
        StringBuilder prompt = new StringBuilder("Professional product photo of ");
        
        switch (category.toLowerCase()) {
            case "jeans":
                prompt.append(buildJeansPrompt(options));
                break;
            case "winter":
                prompt.append(buildWinterPrompt(options));
                break;
            case "accessories":
                prompt.append(buildAccessoriesPrompt(options));
                break;
            default:
                prompt.append("custom apparel");
        }
        
        prompt.append(", dark moody aesthetic, studio lighting, fashion photography, high quality");
        
        return prompt.toString();
    }
    
    private String buildJeansPrompt(Map<String, Object> options) {
        StringBuilder prompt = new StringBuilder();
        
        String fit = getOptionValue(options, "fit", "straight");
        String fabricType = getOptionValue(options, "fabricType", "denim");
        String color = getOptionValue(options, "color", "#222222");
        boolean patches = getBooleanOption(options, "patches");
        boolean embroidery = getBooleanOption(options, "embroidery");
        boolean rips = getBooleanOption(options, "rips");
        
        prompt.append(fit).append(" jeans, ")
              .append(fabricType).append(" material, ")
              .append(color).append(" color");
        
        if (patches) {
            prompt.append(", with decorative patches");
        }
        if (embroidery) {
            prompt.append(", with embroidered details");
        }
        if (rips) {
            prompt.append(", with distressed rips");
        }
        
        return prompt.toString();
    }
    
    private String buildWinterPrompt(Map<String, Object> options) {
        StringBuilder prompt = new StringBuilder();
        
        String style = getOptionValue(options, "style", "hoodie");
        String fabricType = getOptionValue(options, "fabricType", "wool");
        String color = getOptionValue(options, "color", "#303030");
        boolean embroidery = getBooleanOption(options, "embroidery");
        boolean zippers = getBooleanOption(options, "zippers");
        boolean patches = getBooleanOption(options, "patches");
        boolean lining = getBooleanOption(options, "lining");
        
        prompt.append(style).append(" winter wear, ")
              .append(fabricType).append(" material, ")
              .append(color).append(" color");
        
        if (embroidery) {
            prompt.append(", with embroidered details");
        }
        if (zippers) {
            prompt.append(", with extra zipper details");
        }
        if (patches) {
            prompt.append(", with decorative patches");
        }
        if (lining) {
            prompt.append(", with visible removable lining");
        }
        
        return prompt.toString();
    }
    
    private String buildAccessoriesPrompt(Map<String, Object> options) {
        StringBuilder prompt = new StringBuilder();
        
        String accessoryType = getOptionValue(options, "accessoryType", "bag");
        String bagType = getOptionValue(options, "bagType", "");
        String color = getOptionValue(options, "color", "#222222");
        boolean metal = getBooleanOption(options, "metal");
        boolean studs = getBooleanOption(options, "studs");
        boolean stones = getBooleanOption(options, "stones");
        
        prompt.append(accessoryType);
        if (!bagType.isEmpty()) {
            prompt.append(" ").append(bagType);
        }
        // Note: Material is hardcoded to leather for placeholder mode
        // In a real AI implementation, this would be configurable
        prompt.append(", leather material, ")
              .append(color).append(" color");
        
        if (metal) {
            prompt.append(", with metal hardware");
        }
        if (studs) {
            prompt.append(", with metal studs");
        }
        if (stones) {
            prompt.append(", with decorative stones");
        }
        
        return prompt.toString();
    }
    
    /**
     * Get placeholder images based on category
     * Using Unsplash for high-quality SINGLE PRODUCT placeholder images
     */
    private String getPlaceholderImage(String category) {
        switch (category.toLowerCase()) {
            case "jeans":
                // Single pair of jeans on plain background
                return "https://images.unsplash.com/photo-1541099649105-f69ad21f3246?auto=format&fit=crop&w=800&q=80";
            case "winter":
                // Single jacket/hoodie on plain background
                return "https://images.unsplash.com/photo-1591047139829-d91aecb6caea?auto=format&fit=crop&w=800&q=80";
            case "accessories":
                // Single bag on plain background
                return "https://images.unsplash.com/photo-1548036328-c9fa89d128fa?auto=format&fit=crop&w=800&q=80";
            default:
                return "https://images.unsplash.com/photo-1441986300917-64674bd600d8?auto=format&fit=crop&w=800&q=80";
        }
    }
    
    private String getOptionValue(Map<String, Object> options, String key, String defaultValue) {
        Object value = options.get(key);
        return value != null ? value.toString() : defaultValue;
    }
    
    private boolean getBooleanOption(Map<String, Object> options, String key) {
        Object value = options.get(key);
        if (value == null) return false;
        if (value instanceof Boolean) return (Boolean) value;
        if (value instanceof String) {
            String strValue = ((String) value).toLowerCase();
            return strValue.equals("true") || strValue.equals("on");
        }
        return false;
    }
}
