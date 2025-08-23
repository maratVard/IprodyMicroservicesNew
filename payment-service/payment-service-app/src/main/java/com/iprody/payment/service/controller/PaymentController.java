package com.iprody.payment.service.controller;

import com.iprody.payment.service.persistence.PaymentRepository;
import com.iprody.payment.service.persistence.entity.Payment;
import com.iprody.payment.service.persistence.entity.PaymentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentRepository paymentRepository;

    // Получение всех платежей: http://localhost:8088/payments
    @GetMapping
    public List<Payment> getAll() {
        return paymentRepository.findAll();
    }

    // Получение платежа по guid: http://localhost:8088/payments/{guid}
    @GetMapping("/{guid}")
    public ResponseEntity<Payment> getByID(@PathVariable UUID guid) {
        return paymentRepository.findById(guid)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
