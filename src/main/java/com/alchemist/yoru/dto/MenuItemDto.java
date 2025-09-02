package com.alchemist.yoru.dto;

import lombok.Data;

@Data
public class MenuItemDto{

    private long id;

    private String name;

    private String description;

    private double price;

    private byte[] image;

    private String category;

}
