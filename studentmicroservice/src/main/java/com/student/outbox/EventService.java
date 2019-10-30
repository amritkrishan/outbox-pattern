package com.student.outbox;

import com.student.outbox.dao.entity.OutBoxEntity;
import com.student.outbox.dao.entity.OutBoxEvent;
import com.student.outbox.dao.repository.OutBoxRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
public class EventService {

    /**
     * Handle to the Data Access Layer.
     */
    private final OutBoxRepository outBoxRepository;

    /**
     * Autowired constructor.
     *
     * @param outBoxRepository
     */
    @Autowired
    public EventService(OutBoxRepository outBoxRepository) {
        this.outBoxRepository = outBoxRepository;
    }

    /**
     * This method handles all the events fired by the 'EventPublisher'. The method listens to events
     * and persists them in the database.
     *
     * @param event
     */
    @EventListener
    public void handleOutboxEvent(OutBoxEvent event) {

        UUID uuid = UUID.randomUUID();
        OutBoxEntity entity = new OutBoxEntity(
                uuid,
                event.getAggregateId(),
                event.getEventType(),
                event.getPayload().toString(),
                new Date()
        );

        log.info("Handling event : {}.", entity);

        outBoxRepository.save(entity);

        /*
         * Delete the event once written, so that the outbox doesn't grow.
         * The CDC eventing polls the database log entry and not the table in the database.
         */
        outBoxRepository.delete(entity);
    }
}