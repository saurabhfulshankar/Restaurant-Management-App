package com.alchemist.yoru.service;

import com.alchemist.yoru.dto.OrderItemDto;
import com.alchemist.yoru.entity.OrderItem;

import java.util.List;

public interface IOrderItemService {

    OrderItemDto saveOrderItem(OrderItemDto orderItemDto);

    List<OrderItemDto> getOrderItemList();

    OrderItemDto getOrderItemById(long id);


    String deleteOrderItem(long id);

    String permanentDeleteOrderItem(long id);

    OrderItemDto copyEntityToDto(OrderItem orderItem);

    OrderItem copyDtoToEntity(OrderItemDto orderItemDto);
}
