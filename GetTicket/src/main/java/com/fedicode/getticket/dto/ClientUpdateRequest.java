package com.fedicode.getticket.dto;

import lombok.Data;

@Data
public class ClientUpdateRequest {
    private String nom;
    private String prenom;
    private String username;
    private String currentPassword;
    private String newPassword;
}
