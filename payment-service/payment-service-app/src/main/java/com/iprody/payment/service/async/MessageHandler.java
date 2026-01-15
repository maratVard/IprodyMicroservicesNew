package com.iprody.payment.service.async;

public interface MessageHandler<T extends Message> {
    /**
     * Обрабатывает переданное сообщение.
     *
     * @param message сообщение для обработки
     */
    void handle(T message);
}
