package com.alchemist.yoru.controller;

import com.alchemist.yoru.dto.OrderItemDto;
import com.alchemist.yoru.service.impl.OrderItemServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order-item")
public class OrderItemController {

    private final OrderItemServiceImpl orderItemService;

    @PostMapping
    public ResponseEntity<OrderItemDto> createOrderItem(@RequestBody OrderItemDto orderItemDto) {
        OrderItemDto newOrderItem = orderItemService.saveOrderItem(orderItemDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newOrderItem);
    }

    @GetMapping
    public List<OrderItemDto> getAllOrderItems() {
        return orderItemService.getOrderItemList();
    }

    @GetMapping("{id}")
    public ResponseEntity<OrderItemDto> getOrderItemById(@PathVariable("id") long id) {
        OrderItemDto order = orderItemService.getOrderItemById(id);
        return ResponseEntity.ok().body(order);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<OrderItemDto> deleteOrder(@PathVariable("id") long id) {
        orderItemService.deleteOrderItem(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}



