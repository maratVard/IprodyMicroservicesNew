package com.iprody.payment.service.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException{

    private final String operation;
    private final UUID entityId;

    public EntityNotFoundException(String message, String operation, UUID entityId) {
        super(message);
        this.operation = operation;
        this.entityId = entityId;
    }

    public String getOperation() {
        return operation;
    }

    public UUID getEntityId() {
        return entityId;
    }
}
