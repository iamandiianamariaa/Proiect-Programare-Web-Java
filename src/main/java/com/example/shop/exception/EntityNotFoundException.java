package com.example.shop.exception;

import lombok.Builder;

@Builder
public class EntityNotFoundException extends RuntimeException {

    private Long entityId;
    private String entityName;

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }
}
