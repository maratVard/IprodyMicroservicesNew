package org.example.xpaymentadapterapp.async;

import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.OffsetDateTime;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class RequestMessageHandler implements MessageHandler<XPaymentAdapterRequestMessage> {
    private static Logger logger = LoggerFactory.getLogger(RequestMessageHandler.class);

    private final AsyncSender<XPaymentAdapterResponseMessage> sender;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    @Autowired
    public RequestMessageHandler(AsyncSender<XPaymentAdapterResponseMessage> sender) {
        this.sender = sender;
    }
    @Override
    public void handle(XPaymentAdapterRequestMessage message) {
        logger.info("Received request: messageId - {}, amount - {}, currency - {}",
                message.getMessageId(), message.getAmount(), message.getCurrency());
        scheduler.schedule(() -> {
            XPaymentAdapterResponseMessage responseMessage = new XPaymentAdapterResponseMessage();
            responseMessage.setPaymentGuid(message.getPaymentGuid());
            responseMessage.setAmount(message.getAmount());
            responseMessage.setCurrency(message.getCurrency());
            responseMessage.setStatus(XPaymentAdapterStatus.SUCCEEDED);
            responseMessage.setTransactionRefId(UUID.randomUUID());
            responseMessage.setOccurredAt(OffsetDateTime.now());

            logger.info("Sending response: messageId - {}, value - {}, currency - {}",
                    message.getMessageId(), message.getAmount(), message.getCurrency());
            sender.send(responseMessage);
        }, 10, TimeUnit.SECONDS);
    }
    @PreDestroy
    public void shutdown() {
        scheduler.shutdown();
    }
}
