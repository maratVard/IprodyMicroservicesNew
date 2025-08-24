package com.iprody.payment.service.controller;

import com.iprody.payment.service.dto.PaymentDto;
import com.iprody.payment.service.persistence.PaymentFilter;
import com.iprody.payment.service.services.PaymentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

@RestController
@RequestMapping("/payments")
public class PaymentControllerNew {

    private PaymentService paymentService;

    public void PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentDto create(@RequestBody PaymentDto dto) {
        return paymentService.create(dto);
    }

    @GetMapping("/{id}")
    public PaymentDto get(@PathVariable UUID id) {
        return paymentService.get(id);
    }

    @GetMapping
    public Page<PaymentDto> search(PaymentFilter filter, Pageable pageable) {
        return paymentService.search(filter, pageable);
    }
    @PutMapping("/{id}")
    public PaymentDto update(@PathVariable UUID id, @RequestBody
    PaymentDto dto) {
        return paymentService.update(id, dto);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        paymentService.delete(id);
    }
}
