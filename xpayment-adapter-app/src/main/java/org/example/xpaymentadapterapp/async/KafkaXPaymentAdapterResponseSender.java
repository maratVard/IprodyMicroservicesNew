package org.example.xpaymentadapterapp.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;

public class KafkaXPaymentAdapterResponseSender implements AsyncSender<XPaymentAdapterRequestMessage> {
    private static final Logger log = LoggerFactory.getLogger(KafkaXPaymentAdapterResponseSender.class);
    private final KafkaTemplate<String, XPaymentAdapterRequestMessage> template;
    private final String topic;

    public KafkaXPaymentAdapterResponseSender(KafkaTemplate<String,XPaymentAdapterRequestMessage> template,
        @Value("${app.kafka.topics.xpayment-adapter.response:xpayment-adapter.responses}") String topic){
        this.template = template;
        this.topic = topic;
    }
    @Override
    public void send(XPaymentAdapterRequestMessage msg) {
        String key = msg.getPaymentGuid().toString(); // фиксируем партиционирование по платежу
        log.info("Sending XPayment Adapter response: guid={}, amount={}, currency={} -> topic={}",
                msg.getPaymentGuid(), msg.getAmount(), msg.getCurrency(), topic);
        template.send(topic, key, msg);
    }
}
