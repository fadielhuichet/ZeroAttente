package com.fedicode.getticket.service;

import com.fedicode.getticket.entity.Admin;
import com.fedicode.getticket.entity.Client;
import com.fedicode.getticket.entity.Ticket;
import com.fedicode.getticket.repository.AdminRepository;
import com.fedicode.getticket.repository.ClientRepository;
import com.fedicode.getticket.repository.TicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class ClientService {

    private ClientRepository clientRepository;
    private AdminRepository adminRepository;
    private TicketRepository ticketRepository;

//    public void addClient(Client c) {
//        clientRepository.save(c);
//    }
//    public void getNewTicket(Ticket t){
//        ticketRepository.save(t);
//    }

    public List<Admin> getALLOrganizations(){
        return adminRepository.findAll();
    }

//    public List<Admin> searchOrganizationByName(String name) {
//        return adminRepository.findByOrganizationNameContaining(name);
//    }
//
//    public List<Admin> searchOrganizationByLocation(String location) {
//        return adminRepository.findByOrganizationLocationContaining(location);
//    }

    public List<Admin> searchOrganizationByNameAndLocation(String name, String location) {
        return adminRepository.findByOrganizationNameContainingAndOrganizationLocationContaining(name, location);
    }

    public Admin getOrganizationDetails(Long idAdmin) {
        return adminRepository.findById(idAdmin).orElseThrow(() -> new RuntimeException("Organization not found"));
    }

    public Client findByUsername(String username) {
        return clientRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Client not found"));
    }

    public List<Ticket> getTicketsForClient(Long clientId) {
        return ticketRepository.findByClient_IdClient(clientId);
    }


}
