package com.voidapparel.repository;

import com.voidapparel.model.Order;
import com.voidapparel.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    List<Order> findByUserOrderByCreatedAtDesc(User user);
    
    List<Order> findByUserIdOrderByCreatedAtDesc(Long userId);
}
