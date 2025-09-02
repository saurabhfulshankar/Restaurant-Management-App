package com.alchemist.yoru.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
@Entity
@Where(clause = "isActive = true")
public class OrderItem extends BaseEntity{

    @ManyToOne
    @Embedded
    @JoinColumn(name = "menu_item_id",nullable = false)
    private MenuItem menuItem;

    @Column(nullable = false)
    private long quantity;

    @Column(nullable = false)
    private double totalAmount;

    @Column(nullable = false)
    private String tenantId;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id",nullable = false)
    private Order order;
}
