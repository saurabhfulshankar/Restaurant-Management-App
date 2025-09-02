package com.alchemist.yoru.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
@Where(clause = "isActive = true")
@Table(name = "orders")
public class Order extends BaseEntity {

    @Column(name = "order_date",nullable = false)
    private LocalDate orderDate= LocalDate.now();

    @Column(name = "customer_email",nullable = false)
    private String customerEmail;

    @Column(nullable = false)
    private double subtotal;

    @Column(nullable = false)
    private double gstPercentage;

    @Column(nullable = false)
    private double sgst;

    @Column(nullable = false)
    private double cgst;

    @Column(nullable = false)
    private double totalTax;

    @Column(nullable = false)
    private double totalAmount;

    @Column(nullable = false)
    private String tenantId;

    @OneToOne
    @JoinColumn(name = "scratch_card_id")
    private ScratchCard scratchCard;

    @OneToMany(mappedBy = "order",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;
}



