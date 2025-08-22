package com.iprody.payment.service.mapper;

import com.iprody.payment.service.dto.PaymentDto;
import com.iprody.payment.service.persistence.entity.Payment;
import com.iprody.payment.service.persistence.entity.PaymentStatus;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;

public class PaymentMapperTest {

    private final PaymentMapper mapper =
            Mappers.getMapper(PaymentMapper.class);

    @Test
    void shouldMapToDto() {
        // given
        final UUID guid = UUID.randomUUID();
        final UUID inquiryRefId = UUID.randomUUID();
        final BigDecimal amount = new BigDecimal("123.45");
        final String currency = "USD";
        final PaymentStatus status = PaymentStatus.APPROVED;
        final OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC); // фиксируем в UTC

        Payment payment = new Payment();
        payment.setGuid(guid);
        payment.setAmount(amount);
        payment.setCurrency(currency);
        payment.setInquiryRefId(inquiryRefId);
        payment.setStatus(status);
        payment.setCreatedAt(now);
        payment.setUpdatedAt(now);

        // when
        PaymentDto dto = mapper.toDto(payment);

        // then
        assertThat(dto).isNotNull();
        assertThat(dto.getGuid()).isEqualTo(guid);
        assertThat(dto.getAmount()).isEqualTo(amount);
        assertThat(dto.getCurrency()).isEqualTo(currency);
        assertThat(dto.getInquiryRefId()).isEqualTo(inquiryRefId);
        assertThat(dto.getStatus()).isEqualTo(status);

        // сравнение через Instant -> игнорируем таймзону
        assertThat(dto.getCreatedAt()).isEqualTo(payment.getCreatedAt().toInstant());
        assertThat(dto.getUpdatedAt()).isEqualTo(payment.getUpdatedAt().toInstant());
    }

    @Test
    void shouldMapToEntity() {
        //given
        UUID id = UUID.randomUUID();
        OffsetDateTime now = OffsetDateTime.now();

        PaymentDto dto = new PaymentDto(
                id,
                new BigDecimal("999.99"),
                "EUR",
                "ref456",
                PaymentStatus.PENDING,
                now,
                now
        );
        //when
        Payment entity = mapper.toEntity(dto);
        //then
        assertThat(entity).isNotNull();
        assertThat(entity.getGuid()).isEqualTo(dto.getGuid());
        assertThat(entity.getAmount()).isEqualTo(dto.getAmount());
        assertThat(entity.getCurrency()).isEqualTo(dto.getCurrency());
        assertThat(entity.getInquiryRefId()).isEqualTo(dto.getInquiryRefId());
        assertThat(entity.getStatus()).isEqualTo(dto.getStatus());
        assertThat(entity.getCreatedAt()).isEqualTo(dto.getCreatedAt());
        assertThat(entity.getUpdatedAt()).isEqualTo(dto.getUpdatedAt());
    }
}
