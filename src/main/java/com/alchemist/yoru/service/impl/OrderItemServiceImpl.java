package com.alchemist.yoru.service.impl;

import com.alchemist.yoru.Tenant.Context;
import com.alchemist.yoru.exceptions.ResourceNotFoundException;
import com.alchemist.yoru.dto.OrderItemDto;
import com.alchemist.yoru.entity.OrderItem;

import com.alchemist.yoru.repo.OrderItemRepository;
import com.alchemist.yoru.service.IOrderItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderItemServiceImpl implements IOrderItemService {

    private final OrderItemRepository orderItemRepository;

    private static final String order_Item = "OrderItem";

    @Override
    public OrderItemDto saveOrderItem(OrderItemDto orderItemDto) {
        OrderItem orderItem = copyDtoToEntity(orderItemDto);
        orderItem.setTenantId(Context.getTenantId());
        orderItem.setTotalAmount(orderItem.getMenuItem().getPrice()*orderItem.getQuantity());
        return copyEntityToDto(orderItemRepository.save(orderItem));
    }

    @Override
    public List<OrderItemDto> getOrderItemList() {
        return orderItemRepository.findByTenantId(Context.getTenantId()).stream().map(this::copyEntityToDto).collect(Collectors.toList());
    }

    @Override
    public OrderItemDto getOrderItemById(long id) {
        OrderItem orderItem = orderItemRepository.findByIdAndTenantId(id,Context.getTenantId()).orElseThrow( ()
                -> new ResourceNotFoundException("orderItem with"+id+"this id does not exist") );
        return copyEntityToDto(orderItem);
    }

    @Override
    public String deleteOrderItem(long id){
        Optional<OrderItem> orderItem = orderItemRepository.findByIdAndTenantId(id,Context.getTenantId());
        if(orderItem.isPresent()){
            orderItem.get().setActive(false);
            return "Order Item deleted successfully";
        }
        else
            throw new ResourceNotFoundException("orderItem you want to delete with"+id+"id does not exist");
    }

    @Override
    public String permanentDeleteOrderItem(long id) {
        OrderItem orderItem = orderItemRepository.findByIdAndTenantId(id,Context.getTenantId()).orElseThrow( ()
                -> new ResourceNotFoundException("Unable to find the orderItem you want to delete with id :"+id) );
        orderItemRepository.delete(orderItem);
        return "Order Item deleted successfully";
    }

    @Override
    public OrderItemDto copyEntityToDto(OrderItem orderItem) {
        OrderItemDto orderItemDto = new OrderItemDto();
        BeanUtils.copyProperties(orderItem, orderItemDto);
        return orderItemDto;
    }

    @Override
    public OrderItem copyDtoToEntity(OrderItemDto orderItemDto) {
        OrderItem orderItem = new OrderItem();
        BeanUtils.copyProperties(orderItemDto, orderItem);
        return orderItem;
    }

}
