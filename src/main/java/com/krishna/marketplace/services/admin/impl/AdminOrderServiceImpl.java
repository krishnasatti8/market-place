package com.krishna.marketplace.services.admin.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.krishna.marketplace.dto.OrderDto;
import com.krishna.marketplace.enums.OrderStatus;
import com.krishna.marketplace.model.Order;
import com.krishna.marketplace.repository.OrderRepository;
import com.krishna.marketplace.services.admin.AdminOrderService;

@Service
public class AdminOrderServiceImpl implements AdminOrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<OrderDto> getAllPlacedOrders() {

        List<Order> orders = orderRepository
                .findAllByOrderStatusIn(List.of(OrderStatus.Placed, OrderStatus.Shipped, OrderStatus.Delivered));
        return orders.stream().map(Order::getOrderDto).collect(Collectors.toList());
    }

    public OrderDto updateOrderStatus(Long orderId, String status) {

        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            if (Objects.equals(status, "Shipped")) {
                order.setOrderStatus(OrderStatus.Shipped);
            } else if (Objects.equals(status, "Delivered")) {
                order.setOrderStatus(OrderStatus.Delivered);
            }

            return orderRepository.save(order).getOrderDto();

        }
        return null;
    }
}
