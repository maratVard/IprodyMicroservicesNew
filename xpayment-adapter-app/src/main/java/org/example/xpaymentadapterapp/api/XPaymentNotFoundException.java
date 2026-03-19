package org.example.xpaymentadapterapp.api;

import java.util.UUID;

public class XPaymentNotFoundException extends RuntimeException {

    private final UUID chargeId;

    public XPaymentNotFoundException(UUID chargeId) {
        super("No charge found with ID: " + chargeId);
        this.chargeId = chargeId;
    }

    public UUID getChargeId() {
        return chargeId;
    }
}
