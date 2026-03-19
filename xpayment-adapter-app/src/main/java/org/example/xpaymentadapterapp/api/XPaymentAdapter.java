package org.example.xpaymentadapterapp.api;

import org.example.xpaymentadapterapp.api.generated.model.ChargeResponse;
import org.example.xpaymentadapterapp.api.generated.model.CreateChargeRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class XPaymentAdapter {

    ChargeMapper mapper;
    ChargeRepository repository;

    public ChargeResponse create(CreateChargeRequest dto) {
        var entity = repository.save(mapper.toEntity(dto));
        return mapper.toResponse(entity);
    }

    public ChargeResponse retrieve(UUID uuid) {
        return repository.findById(uuid)
                .map(mapper::toResponse)
                .orElseThrow(() -> new XPaymentNotFoundException(uuid));
    }
}
