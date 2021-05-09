package com.example.ticket.facade;

import java.util.Map;
import java.util.List;

import java.util.Optional;

import com.example.ticket.model.EventRepository;
import com.example.ticket.model.Ticket;
import com.example.ticket.model.TicketType;

import com.example.ticket.model.TicketRepository;
import com.example.ticket.model.TicketTypeRepository;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ticket-type")
public class TicketTypeController {
    
    private static final Logger log = LoggerFactory.getLogger(TicketController.class);

    private TicketTypeRepository typeRepo;

    TicketTypeController(
        TicketTypeRepository typeRepo) {
        this.typeRepo = typeRepo;
    }

    @GetMapping()
    public List<TicketType> Get(@RequestParam("eventId") Long eventId) {
        return this.typeRepo.findByEventId(eventId);
    }

    @PostMapping
    public ResponseEntity<Ticket> Post(@RequestBody TicketType ticketType) {
        typeRepo.save(ticketType);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("{id}")
    public void Delete(@PathVariable long id) {
        this.typeRepo.deleteById(id);
    }

}
