package com.krishna.marketplace.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.krishna.marketplace.enums.OrderStatus;
import com.krishna.marketplace.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findByUserIdAndOrderStatus(Long userId, OrderStatus orderStatus);

    List<Order> findAllByOrderStatusIn(List<OrderStatus> orderStatuses);

    List<Order> findByUserIdAndOrderStatusIn(Long userId, List<OrderStatus> orderStatuses);

    Optional<Order> findByTrackingId(UUID trackingId);

}
