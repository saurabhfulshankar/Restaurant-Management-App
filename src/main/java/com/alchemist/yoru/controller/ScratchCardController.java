package com.alchemist.yoru.controller;

import com.alchemist.yoru.dto.OrderDto;
import com.alchemist.yoru.dto.OrderItemDto;
import com.alchemist.yoru.dto.ScratchCardDto;
import com.alchemist.yoru.entity.Order;
import com.alchemist.yoru.entity.OrderItem;
import com.alchemist.yoru.service.impl.ScratchCardServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/scratch-card")
public class ScratchCardController {

    private final ScratchCardServiceImpl scratchCardService;

    @PostMapping("{id}")
    public ResponseEntity<ScratchCardDto> createScratchCard(@PathVariable("id") long id) {
        ScratchCardDto scratchCard= scratchCardService.generateScratchCard(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(scratchCard);
    }

    @GetMapping
    public List<ScratchCardDto> getAllScratchCards() {
        return scratchCardService.getAllScratchCard();
    }

    @GetMapping("{id}")
    public ResponseEntity<ScratchCardDto> getScratchCardById(@PathVariable("id") long id) {
        ScratchCardDto scratchCardDto = scratchCardService.getScratchCard(id);
        return ResponseEntity.ok().body(scratchCardDto);
    }

    @PostMapping("/redeem-card/{code}")
    public ResponseEntity<String> verifyScratchCardCode(@PathVariable("code") long code){
        String result = scratchCardService.redeemedScratchCard(code);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/verifyCode/{code}")
    public ResponseEntity<OrderDto> verifyScratchCardCode(@PathVariable("code") long code,@RequestBody OrderDto order){
        OrderDto orderDto = scratchCardService.verifyScratchCardFromOrder(code,order);
        return ResponseEntity.status(HttpStatus.OK).body(orderDto);
    }

    @GetMapping("/verify/{code}")
    public ResponseEntity<String> verifyScratchCard(@PathVariable("code") long code){
        String message = scratchCardService.verifyScratchCard(code);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Order> deleteScratchCard(@PathVariable("id") long id) {
        scratchCardService.permanentDeleteScratchCard(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
