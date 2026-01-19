package com.fedicode.getticket.service;

import com.fedicode.getticket.dto.TicketResponse;
import com.fedicode.getticket.entity.Admin;
import com.fedicode.getticket.entity.Client;
import com.fedicode.getticket.entity.Ticket;
import com.fedicode.getticket.repository.AdminRepository;
import com.fedicode.getticket.repository.ClientRepository;
import com.fedicode.getticket.repository.TicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@AllArgsConstructor
public class TicketService {

    private TicketRepository ticketRepository;
    private AdminRepository adminRepository;
    private ClientRepository clientRepository;

    public TicketResponse createTicket(Long idAdmin, Long clientId) throws Exception {
        Admin admin = adminRepository.findById(idAdmin)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
        Client client = clientRepository.findById(clientId)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        // Check if client already has an active ticket for this admin
        Ticket existingTicket = ticketRepository.findByAdmin_IdAdminAndClient_IdClient(idAdmin, clientId).orElse(null);
        int currentNumber = admin.getCurrentNumber();

        if (existingTicket != null) {
            // Client already has a ticket, only allow if ticketNumber < currentNumber or currentNumber == 0
            if (!(existingTicket.getTicketNumber() < currentNumber || currentNumber == 0)) {
                throw new IllegalStateException("Cannot get another ticket until your ticket number is less than the current number or the current number is reset to 0.");
            }
            // If allowed, delete the old ticket before creating a new one
            ticketRepository.delete(existingTicket);
        }

        // Get the next ticket number (count all tickets for this admin and add 1)
        List<Ticket> adminTickets = ticketRepository.findByAdmin_IdAdmin(idAdmin);
        int nextNumber = adminTickets.size() + 1;
        
        int remaining = Math.max(nextNumber - currentNumber, 0);
        Ticket t = new Ticket();
        t.setAdmin(admin);
        t.setClient(client);
        t.setTicketNumber(nextNumber);
        t.setRemainingTickets(remaining);
        t.setDateIssued(LocalDate.now().toString());
        t.setTimeIssued(LocalTime.now().toString());
        t.setOrganizationName(admin.getOrganizationName());
        t.setOrganizationLocation(admin.getOrganizationLocation());
        Ticket saved = ticketRepository.save(t);
        return TicketResponse.form(saved);
    }


    @Transactional
    public boolean deleteTicketByAdmin(Long ticketId, Long adminId) {
        long deleted = ticketRepository.deleteByIdTicketAndAdmin_IdAdmin(ticketId, adminId);
        return deleted > 0;
    }

    public boolean deleteTicket(Long ticketId) {
        if (ticketRepository.existsById(ticketId)) {
            ticketRepository.deleteById(ticketId);
            return true;
        }
        return false;
    }

    public List<Ticket> getAllTicketsForClient(Long clientId) {
        return ticketRepository.findByClient_IdClient(clientId);
    }

    public Ticket getTicketDetails(Long idTicket) {
        return ticketRepository.findById(idTicket).orElse(null);
    }

    public List<Ticket> getTicketsByAdminId(Long idAdmin) {
        return ticketRepository.findByAdmin_IdAdmin(idAdmin);
    }
}
