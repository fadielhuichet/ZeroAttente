package com.fedicode.getticket.ControllerTy;

import com.fedicode.getticket.entity.Admin;
import com.fedicode.getticket.entity.Client;
import com.fedicode.getticket.entity.Ticket;
import com.fedicode.getticket.service.AdminService;
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
@RequestMapping("/admin")
public class AdminControllerTy {
    
    private final TicketService ticketService;
    private final AdminService adminService;

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        Admin admin = adminService.findByEmail(userDetails.getUsername());
        List<Ticket> tickets = ticketService.getTicketsByAdminId(admin.getIdAdmin());
        
        model.addAttribute("admin", admin);
        model.addAttribute("tickets", tickets);
        model.addAttribute("currentNumber", admin.getCurrentNumber());
        return "pages/dashboard";
    }

    @PostMapping("/delete-ticket/{idTicket}")
    public String deleteTicket(@PathVariable Long idTicket, 
                               @AuthenticationPrincipal UserDetails userDetails,
                               RedirectAttributes redirectAttributes) {
        Admin admin = adminService.findByEmail(userDetails.getUsername());
        boolean deleted = ticketService.deleteTicketByAdmin(idTicket, admin.getIdAdmin());
        
        if (deleted) {
            redirectAttributes.addFlashAttribute("success", "Ticket deleted successfully");
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to delete ticket");
        }
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/update-current-number")
    public String updateCurrentNumber(@RequestParam int currentNumber,
                                      @AuthenticationPrincipal UserDetails userDetails,
                                      RedirectAttributes redirectAttributes) {
        Admin admin = adminService.findByEmail(userDetails.getUsername());
        admin.setCurrentNumber(currentNumber);
        adminService.save(admin);
        
        redirectAttributes.addFlashAttribute("success", "Current number updated successfully");
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/clients")
    public String viewClients(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        Admin admin = adminService.findByEmail(userDetails.getUsername());
        List<Client> clients = adminService.getClientsWithTicketForAdmin(admin.getOrganizationName());
        
        model.addAttribute("admin", admin);
        model.addAttribute("clients", clients);
        return "pages/admin-clients";
    }
}
