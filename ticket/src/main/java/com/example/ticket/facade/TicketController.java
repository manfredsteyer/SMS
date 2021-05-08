package com.example.ticket.facade;

import java.util.Map;
import java.util.Optional;

import com.example.ticket.model.EventRepository;
import com.example.ticket.model.Ticket;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ticket")
public class TicketController {
    
    private static final Logger log = LoggerFactory.getLogger(TicketController.class);

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    private TicketRepository ticketRepo;
    private TicketTypeRepository typeRepo;

    TicketController(
        TicketRepository ticketRepo,
        TicketTypeRepository typeRepo) {
        this.ticketRepo = ticketRepo;
        this.typeRepo = typeRepo;
    }

    @GetMapping("{id}")
    public Optional<Ticket> Get(@PathVariable long id) {
        var ticket = ticketRepo.findById(id);
        return ticket;
    }

    @KafkaListener(topics = "ticketBooked")
    public void listenWithHeaders(@Payload Ticket ticket) {
        log.info("Received Message: " + ticket);
        this.ticketRepo.save(ticket);
    }

    @PostMapping
    public ResponseEntity<Ticket> Post(@RequestBody Ticket ticket) {
        
        var type = this.typeRepo.findById(ticket.getTicketTypeId()).get();
        ticket.setTicketType(type);
    
        kafkaTemplate
            .send("ticketBooked", ticket)
            .addCallback(
                result -> {
                    log.debug("send message to kafka");
                },
                error -> {
                    log.error("error sending message to kafka", error);
                }
            );

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

}
