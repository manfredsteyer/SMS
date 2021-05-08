package com.example.ticket.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class)
public class Ticket {

    private @Id @GeneratedValue Long ticketId;
    @Column(name = "ticketType_id")
    private Long ticketTypeId;
    private Date purchaseDate;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ticketType_id", insertable = false, updatable = false)
    private TicketType ticketType;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "ticket", cascade = CascadeType.ALL)
    private TicketHolder holder;

    public Ticket() { }

    public Ticket(Date purchaseDate, TicketType ticketType) {
        this.setPurchaseDate(purchaseDate);
        this.setTicketType(ticketType);
    }
    
    public Long getTicketId() {
        return ticketId;
    }
    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }
    public Long getTicketTypeId() {
        return ticketTypeId;
    }
    public void setTicketTypeId(Long ticketTypeId) {
        this.ticketTypeId = ticketTypeId;
    }
    public Date getPurchaseDate() {
        return purchaseDate;
    }
    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public TicketHolder getHolder() {
        return holder;
    }
    public void setHolder(TicketHolder holder) {
        this.holder = holder;
        if (holder != null) {
            holder.setTicket(this);
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((purchaseDate == null) ? 0 : purchaseDate.hashCode());
        result = prime * result + ((ticketId == null) ? 0 : ticketId.hashCode());
        result = prime * result + ((ticketTypeId == null) ? 0 : ticketTypeId.hashCode());
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
        Ticket other = (Ticket) obj;
        if (purchaseDate == null) {
            if (other.purchaseDate != null)
                return false;
        } else if (!purchaseDate.equals(other.purchaseDate))
            return false;
        if (ticketId == null) {
            if (other.ticketId != null)
                return false;
        } else if (!ticketId.equals(other.ticketId))
            return false;
        if (ticketTypeId == null) {
            if (other.ticketTypeId != null)
                return false;
        } else if (!ticketTypeId.equals(other.ticketTypeId))
            return false;
        return true;
    }

    public TicketType getTicketType() {
        return ticketType;
    }
    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
        if (ticketType != null) {
            this.ticketTypeId = ticketType.getTicketTypeId();
        }
    }

    @Override
    public String toString() {
        return "Ticket [purchaseDate=" + purchaseDate + ", ticketId=" + ticketId + ", ticketTypeId=" + ticketTypeId
                + "]";
    }

}