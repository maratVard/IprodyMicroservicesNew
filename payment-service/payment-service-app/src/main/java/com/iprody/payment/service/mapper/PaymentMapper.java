package com.iprody.payment.service.mapper;

import com.iprody.payment.service.dto.PaymentDto;
import com.iprody.payment.service.persistence.entity.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    PaymentDto toDto(Payment payment);
    Payment toEntity(PaymentDto dto);
}
