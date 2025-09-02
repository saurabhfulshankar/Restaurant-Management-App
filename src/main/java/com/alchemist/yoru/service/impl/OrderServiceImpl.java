package com.alchemist.yoru.service.impl;

import com.alchemist.yoru.Tenant.Context;
import com.alchemist.yoru.dto.OrderDto;
import com.alchemist.yoru.dto.OrderItemDto;
import com.alchemist.yoru.entity.Order;
import com.alchemist.yoru.entity.OrderItem;
import com.alchemist.yoru.exceptions.OrderNotFoundException;
import com.alchemist.yoru.exceptions.ResourceNotFoundException;
import com.alchemist.yoru.repo.OrderRepository;
import com.alchemist.yoru.service.IOrderService;
import com.alchemist.yoru.service.ScratchCardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService {

    private final OrderRepository orderRepository;

    private final ScratchCardService scratchCardService;

    private static final String order_String = "Order";

    @Override
    public OrderDto saveOrder(OrderDto orderDto) {
        String tenantId = Context.getTenantId();
        Order order = copyOrderDtoToEntity(orderDto);
        order.setTenantId(tenantId);
        order.setOrderDate(LocalDate.now());
        order.setSgst(2.5* order.getSubtotal()/100);
        order.setCgst(2.5* order.getSubtotal()/100);
        order.setTotalTax(order.getSgst()+order.getCgst());
        order.setTotalAmount(order.getSubtotal()+order.getTotalTax());
        for (OrderItem orderItem : order.getOrderItems()){
            orderItem.setTenantId(tenantId);
            orderItem.setTotalAmount(orderItem.getMenuItem().getPrice()*orderItem.getQuantity());
            orderItem.setOrder(order);
        }
        Order savedOrder = orderRepository.save(order);
        return copyOrderEntityToDto(savedOrder);
    }

    @Override
    public List<OrderDto> getOrderList() {
        String tenantId = Context.getTenantId();
        return orderRepository.findByTenantId(tenantId).stream().map(this::copyOrderEntityToDto).collect(Collectors.toList());
    }

    @Override
    public OrderDto getOrderById(long id) {
        Order order = orderRepository.findByIdAndTenantId(id,Context.getTenantId()).orElseThrow( ()
                
                -> new OrderNotFoundException("Searched Order with id\t"+id+ "\tdoes not exist") );
        return copyOrderEntityToDto(order);
    }


    @Override
    public void deleteOrder(long id) {
        Optional<Order> order = orderRepository.findByIdAndTenantId(id,Context.getTenantId());
        if (order.isPresent()) {
            order.get().setActive(false);
        } else {
            
            throw new OrderNotFoundException("The order you want to delete with"+id+"is already deleted/does not exist");
        }
    }

    @Override
    public void permanentDeleteOrder(long id) {
        Optional<Order> optionalOrder = orderRepository.findByIdAndTenantId(id,Context.getTenantId());
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            orderRepository.delete(order);
        } else {
          
            throw new OrderNotFoundException("The order you want to delete with"+id+"is already deleted/does not exist");
        }
    }


    @Override
    public OrderDto deleteOrderItem(long orderId, OrderItemDto orderItemDto) {
       
        Order order = orderRepository.findByIdAndTenantId(orderId,Context.getTenantId()).orElseThrow(() -> new OrderNotFoundException("order with"+orderId+"this id does not exist"));
        List<OrderItem> orderItems = order.getOrderItems();
        OrderItem orderItem = copyDtoToEntity(orderItemDto);
        orderItems.remove(orderItem);
        order.setOrderItems(orderItems);
        Order newOrder = orderRepository.save(order);
        return copyOrderEntityToDto(newOrder);
    }

    @Override
    public OrderDto addOrderItem(long orderId, OrderItemDto orderItem) {
        
        Order order = orderRepository.findByIdAndTenantId(orderId,Context.getTenantId()).orElseThrow(() -> new OrderNotFoundException("order with id"+orderId+" does not exist"));
        List<OrderItem> orderItems = order.getOrderItems();
        orderItems.add(copyDtoToEntity(orderItem));
        order.setOrderItems(orderItems);
        Order newOrder = orderRepository.save(order);
        return copyOrderEntityToDto(newOrder);
    }

    private OrderDto copyOrderEntityToDto(Order orderEntity) {
        OrderDto orderDto = new OrderDto();
        BeanUtils.copyProperties(orderEntity, orderDto);
        return orderDto;
    }

    private Order copyOrderDtoToEntity(OrderDto orderDto) {
        Order orderEntity = new Order();
        BeanUtils.copyProperties(orderDto, orderEntity);
        return orderEntity;
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


