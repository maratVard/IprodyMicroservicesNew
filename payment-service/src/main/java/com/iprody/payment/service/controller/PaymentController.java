package com.iprody.payment.service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

    @RestController
    @RequestMapping("/payments")
    public class PaymentController {

        private static final Map<Long, Payment> paymentMap = new HashMap<>();

        static {
            paymentMap.put(101L,new Payment(101L, 100.8));
            paymentMap.put(109L,new Payment(109L, 90.2));
            paymentMap.put(808L,new Payment(808L, 440.4));
            paymentMap.put(809L,new Payment(809L, 500.3));
            paymentMap.put(910L,new Payment(910L,999.9));
        }

        @GetMapping
        public ArrayList<Payment> getAll(){

            return new ArrayList<>(paymentMap.values());
        }

        @GetMapping("/{id}")
        public Payment getById(@PathVariable Long id){
            return paymentMap.get(id);

        }
}
