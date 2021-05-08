package com.example.ticket.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.example.ticket.utils.JpaHelper;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(property = "ticketTypeId", generator = ObjectIdGenerators.PropertyGenerator.class)
public class TicketType {

    private @Id @GeneratedValue Long ticketTypeId;
    private String title;

    @Column(name = "event_id", insertable = false, updatable = false)
    private Long eventId;
    private java.math.BigDecimal price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "event_id")
    private Event event;

    public TicketType() { }

    public TicketType(String title, BigDecimal price) {
        this.title = title;
        this.price = price;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ticketType")
    private Set<Ticket> tickets = new HashSet<>();

    public Long getTicketTypeId() {
        return ticketTypeId;
    }
    public void setTicketTypeId(Long ticketTypeId) {
        this.ticketTypeId = ticketTypeId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Long getEventId() {
        return eventId;
    }
    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Event getEvent() {
        return event;
    }
    public void setEvent(Event event) {
        this.event = event;
    }
    
    public Set<Ticket> getTickets() {
        return JpaHelper.unwrap(this.tickets);
    }
    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((eventId == null) ? 0 : eventId.hashCode());
        result = prime * result + ((price == null) ? 0 : price.hashCode());
        result = prime * result + ((ticketTypeId == null) ? 0 : ticketTypeId.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TicketType other = (TicketType) obj;
        if (eventId == null) {
            if (other.eventId != null)
                return false;
        } else if (!eventId.equals(other.eventId))
            return false;
        if (price == null) {
            if (other.price != null)
                return false;
        } else if (!price.equals(other.price))
            return false;
        if (ticketTypeId == null) {
            if (other.ticketTypeId != null)
                return false;
        } else if (!ticketTypeId.equals(other.ticketTypeId))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "TicketType [eventId=" + eventId + ", price=" + price + ", ticketTypeId=" + ticketTypeId + ", title="
                + title + "]";
    }
    
}