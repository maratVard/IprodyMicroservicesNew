package com.iprody.payment.service.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PaymentMassageHandler implements MassageHandler<XPaymentAdapterResponseMessage> {

    private static final Logger logger = LoggerFactory.getLogger(PaymentMassageHandler.class);

    @Override
    public void handle(XPaymentAdapterResponseMessage massage) {
        logger.info("New response massage with id {} and status {}"
                + massage.getMessageId(), massage.getStatus());
    }
}
