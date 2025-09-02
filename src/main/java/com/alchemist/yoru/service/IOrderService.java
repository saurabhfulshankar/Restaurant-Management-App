package com.alchemist.yoru.service;

import com.alchemist.yoru.dto.OrderDto;
import com.alchemist.yoru.dto.OrderItemDto;
import com.alchemist.yoru.entity.OrderItem;

import java.util.List;

public interface IOrderService {
    OrderDto saveOrder(OrderDto orderDto);
    List<OrderDto> getOrderList();
    OrderDto getOrderById(long id);
    void deleteOrder(long id);
    void permanentDeleteOrder(long id);
    OrderDto deleteOrderItem(long orderId, OrderItemDto orderItemDto);
    OrderDto addOrderItem(long orderId, OrderItemDto orderItem);
    OrderItemDto copyEntityToDto(OrderItem orderItem);
    OrderItem copyDtoToEntity(OrderItemDto orderItemDto);
}
