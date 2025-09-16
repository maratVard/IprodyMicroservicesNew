package com.iprody.payment.service.services;

import java.time.Instant;
import java.util.UUID;
import java.util.Objects;

/**
 * DTO-класс для передачи информации об ошибке.
 */
public class ErrorDto {

    private final String error;
    private final Instant timestamp;
    private final String operation;
    private final UUID entityId;

    public ErrorDto(String error, String operation, UUID entityId)
    {
        this.error = error;
        this.timestamp = Instant.now();
        this.operation = operation;
        this.entityId = entityId;
    }

    public String getError() {
        return error;
    }
    public Instant getTimestamp() {
        return timestamp;
    }
    public String getOperation() {
        return operation;
    }
    public UUID getEntityId() {
        return entityId;
    }

    @Override
    public String toString() {
        return "ErrorDto{" +
                "error='" + error + '\'' +
                ", timestamp=" + timestamp +
                ", operation='" + operation + '\'' +
                ", entityId=" + entityId +
                '}';
    }

    /**
     * Enum для допустимых операций над ресурсами.
     */
    public enum Operation {
        CREATE_OP,
        UPDATE_OP,
        FIND_BY_ID_OP,
        FIND_ALL_OP,
        DELETE_OP
    }
}
