package com.iprody.payment.service.controller;

import com.iprody.payment.service.dto.PaymentDto;
import com.iprody.payment.service.persistence.PaymentFilter;
import com.iprody.payment.service.services.PaymentService;
import jakarta.persistence.EntityNotFoundException;
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

    private final PaymentService paymentService;

    public PaymentControllerNew(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    /**
     * Создание нового платежа
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 201
    public PaymentDto create(@RequestBody PaymentDto dto) {
        return paymentService.create(dto);
    }

    /**
     * Получение платежа по ID
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public PaymentDto get(@PathVariable UUID id) {
        try {
            return paymentService.get(id);
        } catch (IllegalArgumentException e) {
            throw new EntityNotFoundException("Платеж не найден: " + id);
        }
    }

    /**
     * Поиск с фильтрацией, сортировкой и пагинацией
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK) // 200
    public Page<PaymentDto> search(PaymentFilter filter, Pageable pageable) {
        return paymentService.search(filter, pageable);
    }

    /**
     * Обновление платежа
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public PaymentDto update(@PathVariable UUID id,
                             @RequestBody PaymentDto dto) {
        return paymentService.update(id, dto);
    }

    /**
     * Удаление платежа
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    public void delete(@PathVariable UUID id) {
        paymentService.delete(id);
    }
}