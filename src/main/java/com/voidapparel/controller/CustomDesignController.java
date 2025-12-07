package com.voidapparel.controller;

import com.voidapparel.dto.CustomDesignRequest;
import com.voidapparel.model.CustomDesign;
import com.voidapparel.model.User;
import com.voidapparel.service.CustomDesignService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/designs")
public class CustomDesignController {
    
    @Autowired
    private CustomDesignService customDesignService;
    
    @PostMapping
    public ResponseEntity<CustomDesign> saveDesign(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody CustomDesignRequest request) {
        return ResponseEntity.ok(customDesignService.saveDesign(user, request));
    }
    
    @GetMapping
    public ResponseEntity<List<CustomDesign>> getUserDesigns(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(customDesignService.getUserDesigns(user));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CustomDesign> getDesignById(
            @AuthenticationPrincipal User user,
            @PathVariable Long id) {
        return ResponseEntity.ok(customDesignService.getDesignById(user, id));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<CustomDesign> updateDesign(
            @AuthenticationPrincipal User user,
            @PathVariable Long id,
            @Valid @RequestBody CustomDesignRequest request) {
        return ResponseEntity.ok(customDesignService.updateDesign(user, id, request));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDesign(
            @AuthenticationPrincipal User user,
            @PathVariable Long id) {
        customDesignService.deleteDesign(user, id);
        return ResponseEntity.noContent().build();
    }
}
