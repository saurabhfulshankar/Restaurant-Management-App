package com.alchemist.yoru.controller;


import com.alchemist.yoru.service.impl.StatisticServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/statistics")
public class StatisticController {

        @Autowired
       StatisticServiceImpl statisticService;
        @GetMapping("/order-placed-value")
        public long getOrderCount() {

                return statisticService.getOrderCount();

        }
        @GetMapping("/total-sell")
        public long getMaxAmount() {

        return statisticService.getTotalAmountFromOrder();

         }

    @GetMapping("/max-price-dish")
    public String getMaxPriceDishPlaced() {

        return statisticService.getMaxPriceDishFromOrder();

    }
    @GetMapping("/frequently-ordered-dish")
    public String getFrequentOrderedDish()
    {
        return statisticService.getFrequentlyOrderedDish();
    }



}
