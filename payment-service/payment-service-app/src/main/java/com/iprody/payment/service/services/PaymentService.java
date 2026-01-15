package com.iprody.payment.service.services;

import com.iprody.payment.service.async.AsyncSender;
import com.iprody.payment.service.async.XPaymentAdapterMapper;
import com.iprody.payment.service.async.XPaymentAdapterRequestMessage;
import com.iprody.payment.service.dto.PaymentDto;
import com.iprody.payment.service.mapper.PaymentMapper;
import com.iprody.payment.service.persistence.PaymentFilter;
import com.iprody.payment.service.persistence.PaymentFilterFactory;
import com.iprody.payment.service.persistence.PaymentRepository;
import com.iprody.payment.service.persistence.entity.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.iprody.payment.service.exeption.EntityNotFoundException;
import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final XPaymentAdapterMapper xPaymentAdapterMapper;
    private final AsyncSender<XPaymentAdapterRequestMessage> sender;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, PaymentMapper paymentMapper, XPaymentAdapterMapper xPaymentAdapterMapper, AsyncSender<XPaymentAdapterRequestMessage> sender) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
        this.xPaymentAdapterMapper = xPaymentAdapterMapper;
        this.sender = sender;
    }

    /**
     * Создание платежа
     */
    public PaymentDto create(PaymentDto dto) {
        Payment entity = paymentMapper.toEntity(dto);
        Payment saved = paymentRepository.save(entity);
        PaymentDto resultDto = paymentMapper.toDto(saved);

        XPaymentAdapterRequestMessage requestMessage =
                xPaymentAdapterMapper.toXPaymentAdapterRequestMessage(entity);
        sender.send(requestMessage);
        return resultDto;
    }

    /**
     * Получение платежа по ID
     */
    public PaymentDto get(UUID id) {
        return paymentRepository.findById(id)
                .map(paymentMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Платеж не найден: ","Update",id));
    }

    /**
     * Поиск платежей с фильтрацией, сортировкой и пагинацией
     */
    public Page<PaymentDto> search(PaymentFilter filter, Pageable pageable) {
        final Specification<Payment> spec = PaymentFilterFactory.fromFilter(filter);
        final Page<Payment> page = paymentRepository.findAll(spec, pageable);
        return page.map(paymentMapper::toDto);
    }

    /**
     * Обновление платежа

    public PaymentDto update(UUID id, PaymentDto dto) {
        final Payment existing = paymentRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Платеж не найден: " + id));

        paymentMapper.updateEntityFromDto(dto, existing);

        final Payment updated = paymentRepository.save(existing);
        return paymentMapper.toDto(updated);
    }
     */
    public PaymentDto update(UUID id, PaymentDto dto) {
        Payment existing = paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Платеж не найден", "update", id));
        Payment updated = paymentMapper.toEntity(dto);
        updated.setInquiryRefId(id);
        Payment saved = paymentRepository.save(updated);
        return paymentMapper.toDto(saved);
    }

    /**
     * Удаление платежа
     */
    public void delete(UUID id) {
        if (!paymentRepository.existsById(id)) {
            throw new EntityNotFoundException("Платеж не найден: ", "Update", id);
        }
        paymentRepository.deleteById(id);
    }
}
