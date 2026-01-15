package com.iprody.payment.service.async;

import com.iprody.payment.service.exeption.EntityNotFoundException;
import com.iprody.payment.service.persistence.PaymentRepository;
import com.iprody.payment.service.persistence.entity.Payment;
import com.iprody.payment.service.persistence.entity.PaymentStatus;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PaymentMessageHandler
        implements MessageHandler<XPaymentAdapterResponseMessage> {

    private static final Logger logger =
            LoggerFactory.getLogger(PaymentMessageHandler.class);

    private final PaymentRepository paymentRepository;

    @Override
    @Transactional
    public void handle(XPaymentAdapterResponseMessage message) {

        UUID paymentGuid = message.getMessageId();
        XPaymentAdapterStatus adapterStatus = message.getStatus();

        Payment payment = (Payment) paymentRepository.findByGuid(paymentGuid)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Payment not found for adapter response",
                        "handle",
                        paymentGuid
                ));

        PaymentStatus newStatus = mapStatus(adapterStatus);

        payment.setStatus(newStatus);
        payment.setUpdatedAt(OffsetDateTime.now());

        paymentRepository.save(payment);

        logger.info(
                "Payment {} updated from adapter: {} -> {}",
                paymentGuid,
                adapterStatus,
                newStatus
        );
    }

    private PaymentStatus mapStatus(XPaymentAdapterStatus status) {
        return switch (status) {
            case SUCCEEDED -> PaymentStatus.RECEIVED;
            case CANCELED  -> PaymentStatus.DECLINED;
            case PROCESSING -> PaymentStatus.PENDING;
        };
    }
}

