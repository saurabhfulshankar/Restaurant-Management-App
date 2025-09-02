package com.alchemist.yoru.service;

import com.alchemist.yoru.dto.OrderDto;
import com.alchemist.yoru.dto.OrderItemDto;
import com.alchemist.yoru.dto.ScratchCardDto;
import com.alchemist.yoru.entity.Order;
import com.alchemist.yoru.entity.OrderItem;
import com.alchemist.yoru.entity.ScratchCard;

import java.util.List;

public interface ScratchCardService {


    ScratchCardDto saveScratchCard(ScratchCardDto scratchCardDto);

    List<ScratchCardDto> getAllScratchCard();

    ScratchCardDto getScratchCard(long id);

    String deleteScratchCard(long id);

    String permanentDeleteScratchCard(long id);

    String generateProbabilityArray();

    String verifyScratchCard(long code);

    String redeemedScratchCard(long code);

    OrderDto verifyScratchCardFromOrder(long code, OrderDto orderDto);

    ScratchCardDto generateScratchCard(long id);

    long generateRandomCode(int length);

    long getReward(Order order);

    ScratchCardDto entityToDto(ScratchCard scratchCard);

    ScratchCard dtoToEntity(ScratchCardDto scratchCardDto);

    List<ScratchCardDto> entityListToDTOList(List<ScratchCard> scratchCardList);
}
