package com.fedicode.getticket.dto;

import com.fedicode.getticket.entity.Ticket;

public record TicketResponse(
        Long idTicket,
        int ticketNumber,
        int remainingTickets,
        String organizationName,
        String organizationLocation,
        String dateIssued,
        String timeIssued
) {
    public static TicketResponse form(Ticket t){
        return new TicketResponse(
                t.getIdTicket(),
                t.getTicketNumber(),
                t.getRemainingTickets(),
                t.getAdmin().getOrganizationName(),
                t.getAdmin().getOrganizationLocation(),
                t.getDateIssued(),
                t.getTimeIssued()
        );
    }
}
