package com.iprody.payment.service.service;

import com.iprody.payment.service.dto.PaymentDto;
import com.iprody.payment.service.mapper.PaymentMapper;
import com.iprody.payment.service.persistence.PaymentFilter;
import com.iprody.payment.service.persistence.PaymentRepository;
import com.iprody.payment.service.persistence.entity.Payment;
import com.iprody.payment.service.persistence.entity.PaymentStatus;
import com.iprody.payment.service.services.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private PaymentMapper paymentMapper;
    @InjectMocks
    private PaymentService paymentService;

    private Payment payment;
    private PaymentDto paymentDto;
    private UUID guid;

    @BeforeEach
    void setUp() {
        //given
        guid = UUID.randomUUID();
        payment = new Payment();
        payment.setGuid(guid);
        payment.setInquiryRefId(UUID.randomUUID());
        payment.setAmount(new BigDecimal("100.00"));
        payment.setCurrency("USD");
        payment.setStatus(PaymentStatus.APPROVED);
        payment.setCreatedAt(OffsetDateTime.now());
        payment.setUpdatedAt(OffsetDateTime.now());

        paymentDto = new PaymentDto();
        paymentDto.setGuid(payment.getGuid());
        paymentDto.setCurrency(payment.getCurrency());
        paymentDto.setStatus(payment.getStatus());
    }

    @Test
    void shouldReturnPaymentById() {
        //given
        when(paymentRepository.findById(guid)).thenReturn(Optional.of(payment));
        when(paymentMapper.toDto(payment)).thenReturn(paymentDto);
        //when
        PaymentDto result = paymentService.get(guid);
        //then
        assertEquals(guid, result.getGuid());
        assertEquals("USD", result.getCurrency());
        assertEquals(PaymentStatus.APPROVED, result.getStatus());
    }

    @Test
    void shouldCreatePayment() {
        // given
        when(paymentMapper.toEntity(paymentDto)).thenReturn(payment);
        when(paymentRepository.save(payment)).thenReturn(payment);
        when(paymentMapper.toDto(payment)).thenReturn(paymentDto);

        // when
        PaymentDto result = paymentService.create(paymentDto);

        // then
        assertEquals(paymentDto, result);
        verify(paymentMapper).toEntity(paymentDto);
        verify(paymentRepository).save(payment);
        verify(paymentMapper).toDto(payment);
    }

    void shouldUpdatePayment() {
        // given
        when(paymentRepository.findById(guid)).thenReturn(Optional.of(payment));
        // маппер обновляет entity "на месте"
        doAnswer(invocation -> {
            PaymentDto dto = invocation.getArgument(0);
            Payment entity = invocation.getArgument(1);
            entity.setCurrency(dto.getCurrency());
            return null;
        }).when(paymentMapper).updateEntityFromDto(any(PaymentDto.class), eq(payment));

        when(paymentRepository.save(payment)).thenReturn(payment);
        when(paymentMapper.toDto(payment)).thenReturn(paymentDto);

        // when
        paymentDto.setCurrency("EUR");
        PaymentDto result = paymentService.update(guid, paymentDto);

        // then
        assertEquals("EUR", result.getCurrency());
        verify(paymentRepository).findById(guid);
        verify(paymentMapper).updateEntityFromDto(paymentDto, payment);
        verify(paymentRepository).save(payment);
    }

    @Test
    void shouldDeletePayment() {
        // given
        when(paymentRepository.existsById(guid)).thenReturn(true);

        // when
        paymentService.delete(guid);

        // then
        verify(paymentRepository).deleteById(guid);
    }

    @Test
    void shouldSearchPayments() {
        // given
        Pageable pageable = PageRequest.of(0, 25);
        Page<Payment> page = new PageImpl<>(List.of(payment));

        when(paymentRepository.findAll(any(Specification.class), eq(pageable)))
                .thenReturn(page);
        when(paymentMapper.toDto(payment)).thenReturn(paymentDto);

        // when
        Page<PaymentDto> result = paymentService.search(new PaymentFilter(), pageable);

        // then
        assertEquals(1, result.getTotalElements());
        assertEquals(paymentDto, result.getContent().get(0));
        verify(paymentRepository).findAll(any(Specification.class), eq(pageable));
    }

    @ParameterizedTest
    @MethodSource("statusProvider")
    void shouldMapDifferentPaymentStatuses(PaymentStatus status) {
        //given
        payment.setStatus(status);
        paymentDto.setStatus(status);

        when(paymentRepository.findById(guid)).thenReturn(Optional.of(payment));
        when(paymentMapper.toDto(payment)).thenReturn(paymentDto);
        //when
        PaymentDto result = paymentService.get(guid);
        //then
        assertEquals(status, result.getStatus());
    }

    static Stream<PaymentStatus> statusProvider() {
        return Stream.of(
                PaymentStatus.RECEIVED,
                PaymentStatus.PENDING,
                PaymentStatus.APPROVED,
                PaymentStatus.DECLINED,
                PaymentStatus.NOT_SENT
        );
    }
}
