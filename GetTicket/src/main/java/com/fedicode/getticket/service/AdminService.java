package com.fedicode.getticket.service;

import com.fedicode.getticket.entity.Admin;
import com.fedicode.getticket.entity.Client;
import com.fedicode.getticket.entity.Ticket;
import com.fedicode.getticket.repository.AdminRepository;
import com.fedicode.getticket.repository.ClientRepository;
import com.fedicode.getticket.repository.TicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AdminService {
    private ClientRepository clientRepository;
    private AdminRepository adminRepository;
    private TicketRepository ticketRepository;

    public List<Ticket> getTicketsByAdminId(Long idAdmin) {
        return ticketRepository.findByAdmin_IdAdmin(idAdmin);
    }



    public List<Client> getAllClients(Long idAdmin)
    {
        return clientRepository.findAllByAdmin_IdAdmin(idAdmin);

    }

    // Utility method to get all clients who have a ticket for a given admin (by organization name)
    public List<Client> getClientsWithTicketForAdmin(String organizationName) {
        // Find all tickets for this organization
        List<Ticket> tickets = ticketRepository.findByOrganizationName(organizationName);
        // Extract unique clients from those tickets
        return tickets.stream()
                .map(Ticket::getClient)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Admin> searchOrganizationByNameAndLocation(String name, String location) {
        return adminRepository.findByOrganizationNameContainingAndOrganizationLocationContaining(
                name != null ? name : "", 
                location != null ? location : ""
        );
    }

    public Admin getOrganizationDetails(Long idAdmin) {
        return adminRepository.findById(idAdmin).orElseThrow(() -> new RuntimeException("Organization not found"));
    }

    public Admin findByEmail(String email) {
        return adminRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Admin not found"));
    }

    public List<Admin> getAllOrganizations() {
        return adminRepository.findAll();
    }

    public Admin save(Admin admin) {
        return adminRepository.save(admin);
    }
}
