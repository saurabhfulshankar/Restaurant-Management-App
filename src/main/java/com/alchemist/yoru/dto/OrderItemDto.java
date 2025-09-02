package com.alchemist.yoru.dto;

import com.alchemist.yoru.entity.MenuItem;
import com.alchemist.yoru.entity.Order;
import lombok.Data;

@Data
public class OrderItemDto {

    private long id;

    private MenuItem menuItem;

    private long quantity;

    private long totalAmount;

    private Order order;
}
