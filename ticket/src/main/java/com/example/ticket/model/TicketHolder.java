package com.example.ticket.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(indexes= {
    @Index(columnList = "ticket_id", unique = true)
})
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class)
public class TicketHolder {
    private @Id @GeneratedValue Long ticketHolderId;

    private String firstName;
    private String lastName;

    public TicketHolder() { }

    public TicketHolder(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;
    
    public Long getTicketHolderId() {
        return ticketHolderId;
    }
    public void setTicketHolderId(Long ticketHolderId) {
        this.ticketHolderId = ticketHolderId;
    }

    public Ticket getTicket() {
        return ticket;
    }
    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        result = prime * result + ((ticketHolderId == null) ? 0 : ticketHolderId.hashCode());
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
        TicketHolder other = (TicketHolder) obj;
        if (firstName == null) {
            if (other.firstName != null)
                return false;
        } else if (!firstName.equals(other.firstName))
            return false;
        if (lastName == null) {
            if (other.lastName != null)
                return false;
        } else if (!lastName.equals(other.lastName))
            return false;
        if (ticketHolderId == null) {
            if (other.ticketHolderId != null)
                return false;
        } else if (!ticketHolderId.equals(other.ticketHolderId))
            return false;
        return true;
    }
    @Override
    public String toString() {
        return "TicketHolder [firstName=" + firstName + ", lastName=" + lastName + ", ticket=" + ticket
                + ", ticketHolderId=" + ticketHolderId + "]";
    }
    
}
