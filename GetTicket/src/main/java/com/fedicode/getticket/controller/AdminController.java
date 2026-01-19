package com.fedicode.getticket.controller;

import com.fedicode.getticket.entity.Admin;
import com.fedicode.getticket.entity.Client;
import com.fedicode.getticket.entity.Ticket;
import com.fedicode.getticket.repository.AdminRepository;
import com.fedicode.getticket.repository.TicketRepository;
import com.fedicode.getticket.service.AdminService;
import com.fedicode.getticket.service.TicketService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/Admin")
public class AdminController{
    private final TicketRepository ticketRepository;
    private TicketService ticketService;
    private AdminService adminService;
    private AdminRepository adminRepository;

    @GetMapping("/tickets")
    public List<Ticket> getAllTicket(@RequestParam Long idAdmin){
        return adminService.getTicketsByAdminId(idAdmin);
    }
    @GetMapping("/clients")
    public List<Client> getAllClient(@RequestParam Long id){
        return adminService.getAllClients(id);
    }
    @PutMapping("/updateCurrentNumberTicket/{adminId}")
    public ResponseEntity<Integer> updateCurrentNumberTicket(@PathVariable Long adminId, @RequestBody Integer newNumber) {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new IllegalArgumentException("Admin not found"));
        admin.setCurrentNumber(newNumber);
        adminRepository.save(admin);
        return ResponseEntity.ok(newNumber);
    }
    @GetMapping("/clients-by-organisation")
    public List<Client> getClientsWithTicketForAdmin(@RequestParam String organisationName) {
        return adminService.getClientsWithTicketForAdmin(organisationName);
    }

    @DeleteMapping("/delete/{idTicket}")
    public ResponseEntity<?> deleteTicket(@PathVariable("idTicket") Long ticketId) {
        boolean deleted = ticketService.deleteTicket(ticketId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(404).body("Ticket not found");
        }
    }

    @GetMapping("/search")
    public List<Admin> searchOrganizationByNameAndLocation(@RequestParam String name, @RequestParam String location) {
        return adminService.searchOrganizationByNameAndLocation(name, location);
    }
}
