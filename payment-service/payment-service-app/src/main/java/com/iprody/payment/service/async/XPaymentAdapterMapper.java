package com.iprody.payment.service.async;

import com.iprody.payment.service.persistence.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface XPaymentAdapterMapper {
    @Mapping(source = "guid", target = "paymentGuid")
    @Mapping(source = "amount", target = "amount")
    @Mapping(source = "currency", target = "currency")
    @Mapping(source = "updatedAt", target = "occurredAt")
    XPaymentAdapterRequestMessage toXPaymentAdapterRequestMessage (Payment payment);
}
