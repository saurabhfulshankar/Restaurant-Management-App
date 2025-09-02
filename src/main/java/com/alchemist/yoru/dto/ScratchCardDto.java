package com.alchemist.yoru.dto;

import com.alchemist.yoru.entity.MenuItem;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
public class ScratchCardDto{

    private long id;

    private long code;

    private boolean isRedeemed;

    private LocalDate expiryDate;

    private MenuItem menuItem;

}
