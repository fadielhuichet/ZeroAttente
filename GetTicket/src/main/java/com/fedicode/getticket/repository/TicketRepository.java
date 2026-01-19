package com.fedicode.getticket.repository;

import com.fedicode.getticket.entity.Admin;
import com.fedicode.getticket.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket,Long> {

    List<Ticket> findByAdmin_IdAdmin(Long idAdmin);

    int countByAdmin(Admin admin);

    Optional<Ticket> findByAdmin_IdAdminAndClient_IdClient(Long idAdmin, Long idClient);

    List<Ticket> findByOrganizationName(String organizationName);

    long deleteByIdTicketAndAdmin_IdAdmin(Long idTicket, Long idAdmin);

    List<Ticket> findByClient_IdClient(Long clientId);
}
