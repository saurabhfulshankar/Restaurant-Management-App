package com.alchemist.yoru.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
@Entity
@Embeddable
@Where(clause = "isActive = true")
@Table(name = "menu_items")
public class MenuItem  extends BaseEntity{

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column(nullable = false)
    private double price;

    @Column
    @Lob
    private byte[] image;

    @Column
    private String category;

    @Column(nullable = false)
    private String tenantId;

    @JsonIgnore
    @OneToMany(mappedBy = "menuItem")
    private List<OrderItem> orderItemList;

    @JsonIgnore
    @OneToMany(mappedBy = "menuItem")
    private List<ScratchCard> scratchCards;

}

