package com.student.outbox.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OUTBOX")
public class OutBoxEntity {

    @Id
    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "aggregateId")
    private Integer aggregateId;

    @Column(name = "eventType")
    private String eventType;

    @Column(name = "payload")
    private String payload;

    @Column(name = "createdOn")
    private Date createdOn;
}