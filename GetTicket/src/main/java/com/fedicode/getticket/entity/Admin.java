package com.fedicode.getticket.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAdmin;

    @Column(nullable = false)
    private int currentNumber;

    @Column(nullable = false)
    private String organizationName;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private String organizationLocation;
    @Column(nullable = false, unique = true)
    private String email;
    private String phone;
    @Column(nullable = false)
    private String organizationCode;

    @OneToMany(mappedBy ="admin",fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Client> client;

    @OneToMany(mappedBy="admin")
    @JsonIgnore
    private List<Ticket> tickets;


}
