package com.voidapparel.repository;

import com.voidapparel.model.CustomDesign;
import com.voidapparel.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomDesignRepository extends JpaRepository<CustomDesign, Long> {
    
    List<CustomDesign> findByUserOrderByCreatedAtDesc(User user);
    
    List<CustomDesign> findByUserIdOrderByCreatedAtDesc(Long userId);
    
    Optional<CustomDesign> findByIdAndUserId(Long id, Long userId);
}
