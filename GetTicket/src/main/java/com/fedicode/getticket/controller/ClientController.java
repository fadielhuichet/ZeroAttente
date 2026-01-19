package com.fedicode.getticket.controller;


import com.fedicode.getticket.dto.TicketResponse;
import com.fedicode.getticket.entity.Admin;
import com.fedicode.getticket.service.ClientService;
import com.fedicode.getticket.service.TicketService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
@AllArgsConstructor
public class ClientController
{
    private ClientService clientService;
    private TicketService ticketService;

    @GetMapping("/search")
    public List<Admin> searchOrganizationByNameAndLocation(@RequestParam String name, @RequestParam String location) {
        return clientService.searchOrganizationByNameAndLocation(name, location);
    }

    @GetMapping("/organisations")
    public List<Admin> getAllOrganizations(){
        return clientService.getALLOrganizations();
    }
    @GetMapping("/organisation/{id}")
    public Admin getOrganization(@PathVariable Long id){
        return clientService.getOrganizationDetails(id);
    }
    @PostMapping("/organisation/{adminId}/get-ticket/{clientId}")
    public ResponseEntity<TicketResponse> getTicket(@PathVariable Long adminId, @PathVariable Long clientId) throws Exception {
        TicketResponse ticket = ticketService.createTicket(adminId, clientId);
        return ResponseEntity.status(HttpStatus.CREATED).body(ticket);
    }

}
