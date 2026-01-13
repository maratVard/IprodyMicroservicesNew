package com.iprody.payment.service.async;

import java.time.OffsetDateTime;
import java.util.UUID;

public interface Message {
    /**
     * Интерфейс, представляющий сообщение с уникальным
     * идентификатором и временем возникновения.
     * Возвращает уникальный идентификатор сообщения.
     *
     * @return UUID сообщения
     */
    UUID getMessageId();

    /**
     * Возвращает время возникновения сообщения.
     *
     * @return момент времени возникновения сообщения
     */
    OffsetDateTime getOccurredAt();
}