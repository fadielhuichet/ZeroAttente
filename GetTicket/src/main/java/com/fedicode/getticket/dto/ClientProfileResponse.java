package com.fedicode.getticket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientProfileResponse {
    private Long idClient;
    private String nom;
    private String prenom;
    private String username;
}
