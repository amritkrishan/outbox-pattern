package com.student.outbox.dao.entity;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OutBoxEvent {

    private Integer aggregateId;

    private String eventType;

    private JsonNode payload;
}