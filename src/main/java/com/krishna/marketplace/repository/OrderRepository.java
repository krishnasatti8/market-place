package com.krishna.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krishna.marketplace.enums.OrderStatus;
import com.krishna.marketplace.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findByUserIdAndOrderStatus(Long userId, OrderStatus orderStatus);


}
