package com.fedicode.getticket.dto;

import lombok.Data;

@Data
public class AdminSignUpRequest {
    private String email;
    private String password;
    private String organizationName;
    private String organizationLocation;
    private String organizationCode;
    private String phone;

}
