package com.student.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.student.dao.entity.StudentEntity;
import com.student.outbox.dao.entity.OutBoxEvent;


/**
 * Utility class to help the service in building event payloads.
 *
 * @author Sohan
 */
public class EventUtils {

    /**
     * Create the event object to be published when new student is enrolled.
     *
     * @param studentEntity
     * @return OutboxEvent
     */
    public static OutBoxEvent createEnrollEvent(StudentEntity studentEntity) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.convertValue(studentEntity, JsonNode.class);

        return new OutBoxEvent(
                studentEntity.getStudentId(),
                "STUDENT_ENROLLED",
                jsonNode
        );
    }

    /**
     * Create the event object to be published when student email is changed.
     *
     * @param studentEntity
     * @return OutboxEvent
     */
    public static OutBoxEvent createUpdateEmailEvent(StudentEntity studentEntity) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode jsonNode = mapper.createObjectNode()
                .put("studentId", studentEntity.getStudentId())
                .put("email",studentEntity.getEmail());

        return new OutBoxEvent(
                studentEntity.getStudentId(),
                "STUDENT_EMAIL_CHANGED",
                jsonNode
        );
    }
}