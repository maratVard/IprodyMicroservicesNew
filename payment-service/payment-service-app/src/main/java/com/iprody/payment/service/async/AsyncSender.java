package com.iprody.payment.service.async;

public interface AsyncSender<T extends Massage> {
    /**
     * Отправляет сообщение.
     *
     * @param message сообщение для отправки
     */
    void send(T message);
}
