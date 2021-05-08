package com.example.ticket.model;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

  @Bean
  Hibernate5Module hibernate5Module() {
    return new Hibernate5Module();
  }

  @Bean
  CommandLineRunner initDatabase(EventRepository eventRepo, TicketRepository ticketRepo) {

    return args -> {
      
      var event1 = new Event(1L, "Spring Break", new Date(), new Date());
      var ticketType1 = new TicketType("Single Ticket", new BigDecimal(500));
      var ticketType2 = new TicketType("Group Ticket", new BigDecimal(1500));
      event1.addTicketType(ticketType1);
      event1.addTicketType(ticketType2);

      var event2 = new Event(2L, "Micro Services Workshop", new Date(), new Date());
      var ticketType3 = new TicketType("Single Ticket", new BigDecimal(500));
      var ticketType4 = new TicketType("Group Ticket", new BigDecimal(1500));
      event2.addTicketType(ticketType3);
      event2.addTicketType(ticketType4);

      log.info("Create Entity: " + eventRepo.save(event1));
      log.info("Create Entity: " + eventRepo.save(event2));

      event1 = eventRepo.findWithTicketTypesByEventId(event1.getEventId());
      event2 = eventRepo.findWithTicketTypesByEventId(event2.getEventId());
      ticketType1 = (TicketType) event1.getTicketTypes().toArray()[0];
      ticketType2 = (TicketType) event1.getTicketTypes().toArray()[1];
      ticketType3 = (TicketType) event2.getTicketTypes().toArray()[0];

      var ticket1 = new Ticket(new Date(), ticketType1);
      ticket1.setHolder(new TicketHolder("John", "Doe"));

      var ticket2 = new Ticket(new Date(), ticketType1);
      ticket2.setHolder(new TicketHolder("Jane", "Doe"));
      
      var ticket3 = new Ticket(new Date(), ticketType3);
      ticket3.setHolder(new TicketHolder("Mike", "Doe"));

      log.info("Create Entity: " + ticketRepo.save(ticket1));
      log.info("Create Entity: " + ticketRepo.save(ticket2));
      log.info("Create Entity: " + ticketRepo.save(ticket3));

    };
  }
}
