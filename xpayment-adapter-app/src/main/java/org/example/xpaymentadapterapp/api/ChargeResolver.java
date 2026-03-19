package org.example.xpaymentadapterapp.api;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
@EnableScheduling
public class ChargeResolver {

    private final ChargeRepository chargeRepository;

    public ChargeResolver(ChargeRepository chargeRepository) {
        this.chargeRepository = chargeRepository;
    }

    @Scheduled(fixedDelayString = "${xpayment.processing.delay-ms:60000}")
    public void processPayments() {
        List<Charge> processingCharges = chargeRepository.findByStatus(Charge.Status.PROCESSING);
        for (Charge charge : processingCharges) {
            if (charge.getAmount().compareTo(BigDecimal.valueOf(100)) == 0) {
                charge.setStatus(Charge.Status.SUCCEEDED);
            } else if (charge.getAmount().compareTo(BigDecimal.valueOf(200)) == 0) {
                charge.setStatus(Charge.Status.CANCELED);
            } else {
                charge.setStatus(Charge.Status.SUCCEEDED);
            }
            charge.setChargedAt(Instant.now());
            chargeRepository.save(charge);
        }
    }
}