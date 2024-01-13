package com.krishna.marketplace.services.admin;

import java.util.List;

import com.krishna.marketplace.dto.OrderDto;

public interface AdminOrderService {
    public List<OrderDto> getAllPlacedOrders();

    public OrderDto updateOrderStatus(Long orderId, String status);
}
