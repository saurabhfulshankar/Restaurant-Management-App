package com.alchemist.yoru.controller;

import com.alchemist.yoru.dto.OrderDto;
import com.alchemist.yoru.dto.OrderItemDto;
import com.alchemist.yoru.entity.Order;
import com.alchemist.yoru.entity.OrderItem;
import com.alchemist.yoru.service.IOrderService;
import com.alchemist.yoru.service.ScratchCardService;
import com.alchemist.yoru.service.impl.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final IOrderService orderService;
    private final ScratchCardService scratchCardService;

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto) {
        OrderDto newOrder = orderService.saveOrder(orderDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newOrder);
    }

    @GetMapping
    public List<OrderDto> getAllOrders() {
        return orderService.getOrderList();
    }

    @GetMapping("{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable("id") long id) {
        OrderDto order = orderService.getOrderById(id);
        return ResponseEntity.ok().body(order);
    }


    @PutMapping("delete-order-item/{orderId}")
    public ResponseEntity<OrderDto> deleteOrderItem(@PathVariable("orderId") long orderId,@RequestBody OrderItemDto orderItemDto) {
        return new ResponseEntity<>(orderService.deleteOrderItem(orderId,orderItemDto),HttpStatus.OK);
    }

    @PutMapping("{orderId}/add-order-item/{orderItemId}")
    public ResponseEntity<OrderDto> addOrderItem(@PathVariable("orderId") long orderId,@RequestBody OrderItemDto orderItem){
        return new ResponseEntity<>(orderService.addOrderItem(orderId,orderItem),HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Order> deleteOrder(@PathVariable("id") long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}



