package com.fedicode.getticket.repository;

import com.fedicode.getticket.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client,Long> {

    Optional<Client> findByUsername(String username);

    List<Client> findAllByAdmin_IdAdmin(Long idAdmin);

}
