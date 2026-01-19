package com.fedicode.getticket.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idClient;

    private String nom;
    private String prenom;

    @Column(unique=true)
    private String username;

    @Column(nullable = false)
    private String passwordHash;


    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "idAdmin")
    private Admin admin;

    @OneToMany(mappedBy = "client")
    private List<Ticket> tickets;
}
