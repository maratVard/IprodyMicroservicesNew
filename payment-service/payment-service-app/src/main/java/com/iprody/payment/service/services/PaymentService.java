package com.iprody.payment.service.services;

import com.iprody.payment.service.dto.PaymentDto;
import com.iprody.payment.service.mapper.PaymentMapper;
import com.iprody.payment.service.persistence.PaymentFilter;
import com.iprody.payment.service.persistence.PaymentFilterFactory;
import com.iprody.payment.service.persistence.PaymentRepository;
import com.iprody.payment.service.persistence.entity.Payment;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    public PaymentService(PaymentRepository paymentRepository,PaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
    }

    public PaymentDto get(UUID id) {
        return paymentRepository.findById(id)
                .map(paymentMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Платеж не найден: " + id));
    }
    public Page<PaymentDto> searchPaged(PaymentFilter filter, Pageable pageable) {
        Specification<Payment> spec = PaymentFilterFactory.fromFilter(filter);
        Page<Payment> page = paymentRepository.findAll(spec, pageable);
        return page.map(paymentMapper::toDto);
    }

    public PaymentDto update(UUID id, PaymentDto paymentDto) {
        Payment existing = paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Платеж не найден: " + id));

        paymentMapper.updateEntityFromDto(paymentDto, existing);

        Payment updated = paymentRepository.save(existing);
        return paymentMapper.toDto(updated);
    }

    public void delete(UUID id) {
        if (!paymentRepository.existsById(id)) {
            throw new EntityNotFoundException("Платеж не найден: " + id);
        }
        paymentRepository.deleteById(id);
    }
}
