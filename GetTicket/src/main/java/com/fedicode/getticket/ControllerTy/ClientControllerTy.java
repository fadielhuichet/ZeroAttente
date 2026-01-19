package com.fedicode.getticket.ControllerTy;

import com.fedicode.getticket.entity.Admin;
import com.fedicode.getticket.entity.Client;
import com.fedicode.getticket.entity.Ticket;
import com.fedicode.getticket.service.ClientService;
import com.fedicode.getticket.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/client")
public class ClientControllerTy {

    private final TicketService ticketService;
    private final ClientService clientService;

    @GetMapping("/my-tickets")
    public String myTickets(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        Client client = clientService.findByUsername(userDetails.getUsername());
        List<Ticket> tickets = ticketService.getAllTicketsForClient(client.getIdClient());
        
        model.addAttribute("client", client);
        model.addAttribute("tickets", tickets);
        return "pages/my-tickets";
    }

    @GetMapping("/ticket/{idTicket}")
    public String ticketDetails(@PathVariable Long idTicket, 
                                @AuthenticationPrincipal UserDetails userDetails, 
                                Model model) {
        Client client = clientService.findByUsername(userDetails.getUsername());
        Ticket ticket = ticketService.getTicketDetails(idTicket);
        
        if (ticket == null || !ticket.getClient().getIdClient().equals(client.getIdClient())) {
            return "redirect:/client/my-tickets";
        }
        
        model.addAttribute("ticket", ticket);
        model.addAttribute("client", client);
        return "pages/ticket-details";
    }

    @GetMapping("/organizations")
    public String listOrganizations(@RequestParam(required = false) String name,
                                    @RequestParam(required = false) String location,
                                    @AuthenticationPrincipal UserDetails userDetails,
                                    Model model) {
        Client client = clientService.findByUsername(userDetails.getUsername());
        List<Admin> organizations;
        
        if ((name != null && !name.isEmpty()) || (location != null && !location.isEmpty())) {
            organizations = clientService.searchOrganizationByNameAndLocation(
                    name != null ? name : "", 
                    location != null ? location : ""
            );
        } else {
            organizations = clientService.getALLOrganizations();
        }
        
        model.addAttribute("client", client);
        model.addAttribute("organizations", organizations);
        model.addAttribute("searchName", name);
        model.addAttribute("searchLocation", location);
        return "pages/client-organizations";
    }

    @GetMapping("/organization/{id}")
    public String organizationDetails(@PathVariable Long id, 
                                      @AuthenticationPrincipal UserDetails userDetails, 
                                      Model model) {
        Client client = clientService.findByUsername(userDetails.getUsername());
        Admin organization = clientService.getOrganizationDetails(id);
        
        model.addAttribute("client", client);
        model.addAttribute("organization", organization);
        return "pages/client-organization-details";
    }

    @PostMapping("/request-ticket/{organizationId}")
    public String requestTicket(@PathVariable Long organizationId,
                                @AuthenticationPrincipal UserDetails userDetails,
                                RedirectAttributes redirectAttributes) {
        Client client = clientService.findByUsername(userDetails.getUsername());
        
        try {
            ticketService.createTicket(organizationId, client.getIdClient());
            redirectAttributes.addFlashAttribute("success", "Ticket requested successfully!");
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to request ticket: " + e.getMessage());
        }
        
        return "redirect:/client/my-tickets";
    }
}
