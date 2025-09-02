package com.alchemist.yoru.service.impl;

import com.alchemist.yoru.Tenant.Context;
import com.alchemist.yoru.repo.MenuItemRepository;
import com.alchemist.yoru.repo.OrderItemRepository;
import com.alchemist.yoru.repo.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticServiceImpl {
    @Autowired
    public OrderRepository orderRepository;

    @Autowired
    public OrderItemRepository orderItemRepository;

    @Autowired
    public MenuItemRepository menuItemRepository;
    public long getOrderCount() {
        if (orderRepository.findByTenantId(Context.getTenantId()).size()!=0)
            return orderRepository.findOrdersPlaced(Context.getTenantId());
        else
            return 0;
    }
//    public long getMaxAmountFromOrderPlaced() {
//        return orderRepository.findMaxAmountFromOrder();
//    }

    public long getTotalAmountFromOrder() {
        if (orderRepository.findByTenantId(Context.getTenantId()).size()!=0)
            return orderRepository.findTotalAmount(Context.getTenantId());
        else
            return 0;
    }


    public String getMaxPriceDishFromOrder() {
        if (orderRepository.findByTenantId(Context.getTenantId()).size()!=0)
            return menuItemRepository.findMaxAmountDish(Context.getTenantId());
        else
            return "";
    }


    public String getFrequentlyOrderedDish()
    {
        if (orderRepository.findByTenantId(Context.getTenantId()).size()!=0)
            return orderItemRepository.findFrequentlyOrderedDish(Context.getTenantId());
        else
            return "";
    }
}
