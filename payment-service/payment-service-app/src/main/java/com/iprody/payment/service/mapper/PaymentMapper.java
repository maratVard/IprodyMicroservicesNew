package com.iprody.payment.service.mapper;

import com.iprody.payment.service.dto.PaymentDto;
import com.iprody.payment.service.persistence.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.time.Instant;
import java.time.OffsetDateTime;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    PaymentDto toDto(Payment payment);

    Payment toEntity(PaymentDto dto);

    void updateEntityFromDto(PaymentDto paymentDto, @MappingTarget Payment payment);

    default Instant map(OffsetDateTime value) {
        return value == null ? null : value.toInstant();
    }

    default OffsetDateTime map(Instant value) {
        return value == null ? null : OffsetDateTime.ofInstant(value, java.time.ZoneOffset.UTC);
    }
}
