package com.fedicode.getticket.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientSignupRequest {
    private String username;
    private String password;
    private String email;
    private String nom;
    private String prenom;
}
