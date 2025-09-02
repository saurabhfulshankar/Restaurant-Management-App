package com.alchemist.yoru.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString
@Table
@Where(clause = "isActive = true")
public class ScratchCard extends BaseEntity{

    @Column(nullable = false,unique = true)
    private long code;

    @Column(nullable = false)
    private boolean isRedeemed;

    @Column(nullable = false)
    private LocalDate expiryDate;

    @Column(nullable = false)
    boolean isMailSend = false;

    @Column(nullable = false)
    private String tenantId;

    @ManyToOne
    @JoinColumn(name = "menu_item_id")
    private MenuItem menuItem;

    @JsonIgnore
    @OneToOne(mappedBy = "scratchCard")
    @JoinColumn(name = "order_id")
    private Order order;
}
