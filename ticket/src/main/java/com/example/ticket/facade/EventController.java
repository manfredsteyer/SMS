package com.example.ticket.facade;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import com.example.ticket.model.Event;
import com.example.ticket.model.EventRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/event")
public class EventController {

    private static final Logger log = LoggerFactory.getLogger(EventController.class);

    private EventRepository repo;

    public EventController(EventRepository repo) {
        this.repo = repo;
    }

    @GetMapping("{id}")
    public Event Get(@PathVariable long id) {
        return this.repo.findWithTicketTypesByEventId(id);
    }

    @GetMapping()
    public List<Event> Get() {
        return this.repo.findAll(Sort.by("start").descending());
    }

    @PutMapping("{id}")
    public Event Put(@PathVariable long id, @RequestBody Event event) {
        event.setEventId(id);
        return repo.save(event);
    }
    
    @KafkaListener(topics = "eventPublished")
    public void listenWithHeaders(@Payload Event event) {
        log.debug("Received Message: " + event);
        this.repo.save(event);
    }

}
