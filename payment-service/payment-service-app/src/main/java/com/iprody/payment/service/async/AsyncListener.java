package com.iprody.payment.service.async;

public interface AsyncListener<T extends Massage> {
    /**
     * Вызывается для каждого нового входящего сообщения.
     *
     * @param message сообщение для обработки
     */
    void onMessage(T message);
}
