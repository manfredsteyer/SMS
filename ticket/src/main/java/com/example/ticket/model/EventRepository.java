package com.example.ticket.model;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {

    @EntityGraph(attributePaths = "ticketTypes")
    Event findWithTicketTypesByEventId(Long eventId);
}
