package com.iprody.payment.service.controller;

import com.iprody.payment.service.dto.PaymentDto;
import com.iprody.payment.service.persistence.PaymentFilter;
import com.iprody.payment.service.services.ErrorDto;
import com.iprody.payment.service.services.PaymentService;
import com.iprody.payment.service.exeption.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    /**
     * Создание нового платежа
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 201
    @PreAuthorize("hasRole('admin')")
    public PaymentDto create(@RequestBody PaymentDto dto) {
        return paymentService.create(dto);
    }

    /**
     * Получение платежа по ID
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('admin', 'reader')")
    @ResponseStatus(HttpStatus.OK) // 200
    public PaymentDto get(@PathVariable UUID id) {
        try {
            return paymentService.get(id);
        } catch (IllegalArgumentException e) {
            throw new EntityNotFoundException("Платеж не найден: ", "Update",id);
        }
    }

    /**
     * Поиск с фильтрацией, сортировкой и пагинацией
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('admin', 'reader')")
    @ResponseStatus(HttpStatus.OK) // 200
    public Page<PaymentDto> search(PaymentFilter filter, Pageable pageable) {
        return paymentService.search(filter, pageable);
    }

    /**
     * Обновление платежа
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('admin')")
    @ResponseStatus(HttpStatus.OK) // 200
    public PaymentDto update(@PathVariable UUID id, @RequestBody PaymentDto dto) {
        return paymentService.update(id, dto);
    }

    /**
     * Удаление платежа
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('admin')")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    public void delete(@PathVariable UUID id) {
        paymentService.delete(id);
    }

    /**
     * Обновление комментария
     */
    @PatchMapping("/{id}/note")
    @PreAuthorize("hasAnyRole('admin', 'reader')")
    @ResponseStatus(HttpStatus.OK) // 200
    public PaymentDto updateNote(@PathVariable UUID id, @RequestBody String note) {
        // Получаем текущий DTO
        final PaymentDto existing = paymentService.get(id);

        // Обновляем только note
        existing.setNote(note);

        // Сохраняем обновлённый объект через update
        return paymentService.update(id, existing);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto notFoundExeptionHandler(EntityNotFoundException ex) {
        return new ErrorDto (ex.getMessage(),"findById", ex.getEntityId());
    }
}