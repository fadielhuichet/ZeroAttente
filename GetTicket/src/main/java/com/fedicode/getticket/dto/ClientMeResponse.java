package com.fedicode.getticket.dto;

import com.fedicode.getticket.entity.Ticket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientMeResponse {
    private Long idClient;
    private String nom;
    private String prenom;
    private String username;
    private Long clientId;
    private Ticket ticket;
}
