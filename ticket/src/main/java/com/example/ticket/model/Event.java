package com.example.ticket.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.example.ticket.utils.JpaHelper;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity()
@JsonIdentityInfo(property = "eventId", generator = ObjectIdGenerators.PropertyGenerator.class)
public class Event {

    private @Id Long eventId;
    private String title;
    
    private Date start;
    private Date end;

    public Event() {    }

    public Event(Long id, String title, Date start, Date end) {
        this.eventId = id;
        this.title = title;
        this.start = start;
        this.end = end;
    }

    public Event(String title, Date start, Date end) {
        this.title = title;
        this.start = start;
        this.end = end;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "event", cascade = CascadeType.ALL)
    private Set<TicketType> ticketTypes = new HashSet<>();

    public void addTicketType(TicketType ticketType) {
        ticketType.setEvent(this);
        this.ticketTypes.add(ticketType);
    }

    public Long getEventId() {
        return eventId;
    }
    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
   
    public Date getStart() {
        return start;
    }
    public void setStart(Date start) {
        this.start = start;
    }
    public Date getEnd() {
        return end;
    }
    public void setEnd(Date end) {
        this.end = end;
    }

    public Set<TicketType> getTicketTypes() {
        // return JpaHelper.unwrap(this.ticketTypes);
        return this.ticketTypes;
    }
    public void setTicketTypes(Set<TicketType> ticketTypes) {
        this.ticketTypes = ticketTypes;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((eventId == null) ? 0 : eventId.hashCode());
        result = prime * result + ((start == null) ? 0 : start.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + ((end == null) ? 0 : end.hashCode());
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
        Event other = (Event) obj;
        if (eventId == null) {
            if (other.eventId != null)
                return false;
        } else if (!eventId.equals(other.eventId))
            return false;
        if (start == null) {
            if (other.start != null)
                return false;
        } else if (!start.equals(other.start))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        if (end == null) {
            if (other.end != null)
                return false;
        } else if (!end.equals(other.end))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Event [eventId=" + eventId + ", from=" + start + ", title=" + title + ", to=" + end + ", ticket_types=" + this.ticketTypes + "]";
    }

}
