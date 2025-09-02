package com.alchemist.yoru.service.impl;

import com.alchemist.yoru.Tenant.Context;
import com.alchemist.yoru.dto.OrderDto;
import com.alchemist.yoru.dto.ScratchCardDto;
import com.alchemist.yoru.entity.*;
import com.alchemist.yoru.exceptions.ResourceNotFoundException;
import com.alchemist.yoru.repo.*;
import com.alchemist.yoru.service.ScratchCardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScratchCardServiceImpl implements ScratchCardService {

    private final ScratchCardRepository scratchCardRepository;

    private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;

    private final MenuItemRepository menuItemRepository;

    private final ProbabilityArrayRepository probabilityArrayRepository;

    private final Random random = new Random();

    private static final int SUCCESS_RATE = 8;

    private static final String SCRATCH_CARD ="Scratch Card";

    @Override
    public ScratchCardDto saveScratchCard(ScratchCardDto scratchCardDto){
        String tenantId = Context.getTenantId();
        ScratchCard scratchCard = scratchCardRepository.
                save(dtoToEntity(scratchCardDto));
        scratchCard.setTenantId(tenantId);
        return entityToDto(scratchCard);
    }

    @Override
    public List<ScratchCardDto> getAllScratchCard(){
        return entityListToDTOList(scratchCardRepository.findByTenantId(Context.getTenantId()));
    }

    @Override
    public ScratchCardDto getScratchCard(long id){
        Optional<ScratchCard> scratchCard = scratchCardRepository.findByIdAndTenantId(id,Context.getTenantId());
        if (scratchCard.isPresent()){
            return entityToDto(scratchCard.get());
        }
        else
            throw new ResourceNotFoundException("id"+id);
    }


    @Override
    public String deleteScratchCard(long id){
        Optional<ScratchCard> scratchCard = scratchCardRepository.findByIdAndTenantId(id,Context.getTenantId());
        if (scratchCard.isPresent()){
            scratchCard.get().setActive(false);
            scratchCardRepository.save(scratchCard.get());
            return "Scratch Card deleted successfully";
        }
        else
            throw new ResourceNotFoundException("Scratch card with id"+id+"not found");
    }

    @Override
    public String permanentDeleteScratchCard(long id){
        Optional<ScratchCard> scratchCard = scratchCardRepository.findByIdAndTenantId(id,Context.getTenantId());
        if (scratchCard.isPresent()){
            scratchCardRepository.delete(scratchCard.get());
            return "Scratch Card deleted successfully";
        }
        else
            throw new ResourceNotFoundException("id"+id);
    }

    @Override
    public String generateProbabilityArray(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("1".repeat(SUCCESS_RATE));
        stringBuilder.append("0".repeat(10 - SUCCESS_RATE));
        return stringBuilder.toString();
    }

    @Override
    public String verifyScratchCard(long code){
       Optional<ScratchCard> scratchCard = scratchCardRepository.findByCodeAndTenantId(code,Context.getTenantId());
       if (scratchCard.isPresent()){
           if (scratchCard.get().isRedeemed()){
               return "Scratch Card is already redeemed";
           }
           else if (scratchCard.get().getExpiryDate().isBefore(LocalDate.now())){
               return "Scratch Card is expired";
           }
           else{
               return "Scratch Card is valid and not redeemed";
           }
       } else {
           return "Invalid Code!!! Please Enter valid code";
       }
    }

    @Override
    public String redeemedScratchCard(long code){
        ScratchCard scratchCard = scratchCardRepository.findByCodeAndTenantId(code,Context.getTenantId()).orElseThrow(
                ()-> new ResourceNotFoundException("code"+code));
        String result = verifyScratchCard(code);
        if (result =="Scratch Card is valid and not redeemed"){
            scratchCard.setRedeemed(true);
            scratchCardRepository.save(scratchCard);
            return "Scratch Card Redeemed";
        }
        else
            return result;
    }

    @Override
    public OrderDto verifyScratchCardFromOrder(long code, OrderDto orderDto){
        String response = verifyScratchCard(code);
        Order order = copyOrderDtoToEntity(orderDto);
        if (response.equals("Scratch Card is valid and not redeemed")){
            ScratchCard scratchCard = scratchCardRepository.findByCodeAndTenantId(code,Context.getTenantId()).
                    orElseThrow(() -> new ResourceNotFoundException("code"+code));
            MenuItem menuItem = scratchCard.getMenuItem();
            if (menuItem!=null){
                OrderItem orderItem = new OrderItem();
                menuItem.setPrice(0.00);
                orderItem.setTenantId(Context.getTenantId());
                orderItem.setMenuItem(menuItem);
                orderItem.setQuantity(1);
                orderItem.setTotalAmount(0.00);
                List<OrderItem> orderItems= order.getOrderItems();
                orderItems.add(orderItem);
                order.setOrderItems(orderItems);
            }
            scratchCard.setRedeemed(true);
            scratchCard = scratchCardRepository.save(scratchCard);
            order.setScratchCard(scratchCard);
        }
        return copyOrderEntityToDto(order);
    }

    @Override
    public ScratchCardDto generateScratchCard(long id){
        ProbabilityString probabilityString;
        String probabilityArray;
        Optional<ProbabilityString> probability= probabilityArrayRepository.findByTenantId(Context.getTenantId());
        if (probability.isEmpty()){
            probabilityString = new ProbabilityString();
            probabilityString.setProbability(generateProbabilityArray());
            probabilityString.setTenantId(Context.getTenantId());
        }
        else{
            probabilityString = probability.get();
        }
        probabilityArray = probabilityString.getProbability();
        Order order = orderRepository.findByIdAndTenantId(id,Context.getTenantId()).orElseThrow(()->new ResourceNotFoundException("Order not found with ID "+ id));
        ScratchCard scratchCard = new ScratchCard();
        scratchCard.setTenantId(Context.getTenantId());
        scratchCard.setOrder(order);
        long code = generateRandomCode(6);
        while (scratchCardRepository.findByCodeAndTenantId(code,Context.getTenantId()).isPresent()){
            code = generateRandomCode(6);
        }
        scratchCard.setCode(code);
        scratchCard.setExpiryDate(LocalDate.now().plusDays(30));
        if (probabilityArray.isEmpty()){
            probabilityArray = generateProbabilityArray();
        }
        int randomNumber = random.nextInt(probabilityArray.length());
        if (probabilityArray.charAt(randomNumber) =='1'){
            long menuItemId  = getReward(order);
            if (menuItemId>0){
                MenuItem menuItem = menuItemRepository.findByIdAndTenantId(getReward(order),Context.getTenantId())
                        .orElseThrow(()->new ResourceNotFoundException("Menu Item is not found with " + id));
                scratchCard.setMenuItem(menuItem);
                StringBuilder sb = new StringBuilder(probabilityArray);
                sb.deleteCharAt(randomNumber);
                probabilityArray = sb.toString();
            }
        } else{
            StringBuilder sb = new StringBuilder(probabilityArray);
            sb.deleteCharAt(sb.lastIndexOf("0"));
            probabilityArray = sb.toString();
        }
        probabilityString.setProbability(probabilityArray);
        probabilityArrayRepository.save(probabilityString);
        ScratchCard generated = scratchCardRepository.save(scratchCard);
        order.setScratchCard(generated);
        orderRepository.save(order);
        return entityToDto(generated);
    }

    @Override
    public long generateRandomCode(int length){
        long code =0;
        for (int i = 0; i < length; i++) {
            code = code*10+random.nextInt(10);
        }
        return code;
    }

    @Override
    public long getReward(Order order) {
        String tenantId = Context.getTenantId();
        double price = order.getTotalAmount()*15/100;
        List<MenuItem> menuItems =  menuItemRepository.findByPriceLessThanAndTenantId(price,tenantId);
        if (menuItems.isEmpty()) {
            return -1;
        }
        else {
            return menuItems.get(random.nextInt(menuItems.size())).getId();
        }
    }

    @Override
    public ScratchCardDto entityToDto(ScratchCard scratchCard) {
        ScratchCardDto scratchCardDto = new ScratchCardDto();
        BeanUtils.copyProperties(scratchCard, scratchCardDto);
        return scratchCardDto;
    }

    @Override
    public ScratchCard dtoToEntity(ScratchCardDto scratchCardDto) {
        ScratchCard scratchCard = new ScratchCard();
        BeanUtils.copyProperties(scratchCardDto, scratchCard);
        return scratchCard;
    }

    @Override
    public List<ScratchCardDto> entityListToDTOList(List<ScratchCard> scratchCardList){
        return scratchCardList.
                stream().map(this::entityToDto).
                collect(Collectors.toList());
    }

//    @Override
//    public OrderItemDto copyEntityToDto(OrderItem orderItem) {
//        OrderItemDto orderItemDto = new OrderItemDto();
//        BeanUtils.copyProperties(orderItem, orderItemDto);
//        return orderItemDto;
//    }
//
//    @Override
//    public OrderItem copyDtoToEntity(OrderItemDto orderItemDto) {
//        OrderItem orderItem = new OrderItem();
//        BeanUtils.copyProperties(orderItemDto, orderItem);
//        return orderItem;
//    }

    private OrderDto copyOrderEntityToDto(Order orderEntity) {
        OrderDto orderDto = new OrderDto();
        BeanUtils.copyProperties(orderEntity, orderDto);
        return orderDto;
    }

    private Order copyOrderDtoToEntity(OrderDto orderDto) {
        Order orderEntity = new Order();
        BeanUtils.copyProperties(orderDto, orderEntity);
        return orderEntity;
    }

}
