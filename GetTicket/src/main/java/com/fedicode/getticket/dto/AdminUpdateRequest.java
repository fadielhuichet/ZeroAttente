package com.fedicode.getticket.dto;

import lombok.Data;

@Data
public class AdminUpdateRequest {
    private String organizationName;
    private String organizationLocation;
    private String phone;
    private String currentPassword;
    private String newPassword;
}
