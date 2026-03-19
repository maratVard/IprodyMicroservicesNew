package org.example.xpaymentadapterapp.api;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Charge {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private BigDecimal amount;
    private Currency currency;
    private String customer;
    private UUID orderId;
    private String receiptEmail;
    @Enumerated(EnumType.STRING)
    private Status status;
    private Instant createdAt;
    private Instant chargedAt;

    public static enum Status {
        PROCESSING, CANCELED, SUCCEEDED
    }
}