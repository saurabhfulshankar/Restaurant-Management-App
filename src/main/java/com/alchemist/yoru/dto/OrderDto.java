package com.alchemist.yoru.dto;

import com.alchemist.yoru.entity.OrderItem;
import com.alchemist.yoru.entity.ScratchCard;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class OrderDto {

    private long id;

    private LocalDate orderDate;

    private double subtotal;

    private double sgst;

    private double cgst;

    private double totalTax;

    private long code;

    private double totalAmount;

    private ScratchCard scratchcard;

    private String customerEmail;

    private List<OrderItem> orderItems;
}
