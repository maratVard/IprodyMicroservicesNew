package com.iprody.payment.service.async;

public interface MassageHandler <T extends Massage> {
    /**
     * Обрабатывает переданное сообщение.
     *
     * @param message сообщение для обработки
     */
    void handle(T message);
}
