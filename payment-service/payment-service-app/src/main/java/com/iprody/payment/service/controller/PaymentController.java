package com.iprody.payment.service.controller;

import com.iprody.payment.service.persistence.PaymentRepository;
import com.iprody.payment.service.persistence.entity.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    @Autowired
    private PaymentRepository paymentRepository;

    @GetMapping("/all")
    public List<Payment> getAll() {
        return paymentRepository.findAll();
    }

    @GetMapping("/guid")
    public Payment getByID(@PathVariable UUID guid) {
        return new Payment();
    }
}
