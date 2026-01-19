package com.fedicode.getticket.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTicket;


    @Column(nullable = false)
    private int ticketNumber;

    @Column(nullable = false)
    private int remainingTickets;
//    @Transient
//    public int getRemainingTickets(){
//        return Math.max(ticketNumber -admin.getCurrentNumber(),0);
//    }

    private String organizationName;
    private String organizationLocation;

    private String dateIssued;
    private String timeIssued;

    private String descrption;

    @ManyToOne
    @JoinColumn(name = "idAdmin")
    private Admin admin;

    @ManyToOne
    @JoinColumn(name = "idClient")
    private Client client;


}