package com.fedicode.getticket.controller;

import com.fedicode.getticket.dto.ClientSignupRequest;
import com.fedicode.getticket.dto.ClientUpdateRequest;
import com.fedicode.getticket.dto.ClientProfileResponse;
import com.fedicode.getticket.dto.ClientMeResponse;
import com.fedicode.getticket.entity.Client;
import com.fedicode.getticket.entity.Ticket;
import com.fedicode.getticket.repository.ClientRepository;
import com.fedicode.getticket.repository.TicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/client/auth")
@AllArgsConstructor
public class ClientAuthController {
    ClientRepository clientRepository;
    PasswordEncoder passwordEncoder;
    TicketRepository ticketRepository;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody ClientSignupRequest req){
        if(clientRepository.findByUsername(req.getUsername()).isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Client c=new Client();
        c.setUsername(req.getUsername());
        c.setNom(req.getNom());
        c.setPrenom(req.getPrenom());
        c.setPasswordHash(passwordEncoder.encode(req.getPassword()));
        clientRepository.save(c);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@AuthenticationPrincipal UserDetails userDetails, 
                                           @RequestBody ClientUpdateRequest req) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not authenticated");
        }
        
        Client client = clientRepository.findByUsername(userDetails.getUsername()).orElse(null);
        if (client == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client not found");
        }

        if (req.getNom() != null && !req.getNom().trim().isEmpty()) {
            client.setNom(req.getNom().trim());
        }
        if (req.getPrenom() != null && !req.getPrenom().trim().isEmpty()) {
            client.setPrenom(req.getPrenom().trim());
        }
        if (req.getUsername() != null && !req.getUsername().trim().isEmpty() 
                && !req.getUsername().trim().equals(client.getUsername())) {
            String newUsername = req.getUsername().trim();
            if (clientRepository.findByUsername(newUsername).isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already taken");
            }
            client.setUsername(newUsername);
        }

        if (req.getNewPassword() != null && !req.getNewPassword().isEmpty()) {
            if (req.getCurrentPassword() == null || req.getCurrentPassword().isEmpty() ||
                !passwordEncoder.matches(req.getCurrentPassword(), client.getPasswordHash())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Current password is incorrect");
            }
            if (req.getNewPassword().length() < 6) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("New password must be at least 6 characters");
            }
            client.setPasswordHash(passwordEncoder.encode(req.getNewPassword()));
        }

        clientRepository.save(client);
        return ResponseEntity.ok(toProfileResponse(client));
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not authenticated");
        }
        Client client = clientRepository.findByUsername(userDetails.getUsername()).orElse(null);
        if (client == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client not found");
        }
        return ResponseEntity.ok(toProfileResponse(client));
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not authenticated");
        }
        Client client = clientRepository.findByUsername(userDetails.getUsername()).orElse(null);
        if (client == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client not found");
        }
        
        // Fetch the latest ticket for this client
        Ticket ticket = null;
        if (client.getTickets() != null && !client.getTickets().isEmpty()) {
            ticket = client.getTickets().stream()
                    .sorted((a, b) -> Long.compare(b.getIdTicket(), a.getIdTicket()))
                    .findFirst().orElse(null);
        }
        
        ClientMeResponse response = new ClientMeResponse(
                client.getIdClient(),
                client.getNom(),
                client.getPrenom(),
                client.getUsername(),
                client.getIdClient(),
                ticket
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        return ResponseEntity.ok().build();
    }

    private ClientProfileResponse toProfileResponse(Client client) {
        return new ClientProfileResponse(
            client.getIdClient(),
            client.getNom(),
            client.getPrenom(),
            client.getUsername()
        );
    }
}
