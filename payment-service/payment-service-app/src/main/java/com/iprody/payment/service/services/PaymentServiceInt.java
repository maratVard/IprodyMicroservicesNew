package com.iprody.payment.service.services;

import com.iprody.payment.service.dto.PaymentDto;
import com.iprody.payment.service.persistence.PaymentFilter;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import java.util.UUID;

public interface PaymentServiceInt {

    PaymentDto get(UUID id);
    Page<PaymentDto> search(PaymentFilter filter, Pageable pageable);
}
