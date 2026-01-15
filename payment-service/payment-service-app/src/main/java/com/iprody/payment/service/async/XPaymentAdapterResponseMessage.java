package com.iprody.payment.service.async;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class XPaymentAdapterResponseMessage implements Message {
    /**
     * Уникальный идентификатор сообщения.
     */
    private UUID messageGuid;

    /**
     * Уникальный идентификатор платежа.
     */
    private UUID paymentGuid;

    /**
     * Сумма платежа.
     */
    private BigDecimal amount;

    /**
     * Валюта платежа в формате ISO 4217 (например, "USD", "EUR").
     */
    private String currency;

    /**
     * Уникальный идентификатор транзакции в платёжной системе.
     */
    private UUID transactionRefId;

    /**
     * Статус платежа.
     */
    private XPaymentAdapterStatus status;

    /**
     * Момент времени, когда событие произошло.
     */
    private OffsetDateTime occurredAt;

    @Override
    public UUID getMessageId() {
        return messageGuid;
    }

    public UUID getPaymentGuid() {
        return paymentGuid;
    }

    public void setPaymentGuid(UUID paymentGuid) {
        this.paymentGuid = paymentGuid;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public UUID getTransactionRefId() {
        return transactionRefId;
    }

    public void setTransactionRefId(UUID transactionRefId) {
        this.transactionRefId = transactionRefId;
    }

    public XPaymentAdapterStatus getStatus() {
        return status;
    }

    public void setStatus(XPaymentAdapterStatus status) {
        this.status = status;
    }

    @Override
    public OffsetDateTime getOccurredAt() {
        return occurredAt;
    }

    public void setOccurredAt(OffsetDateTime occurredAt) {
        this.occurredAt = occurredAt;
    }
}
