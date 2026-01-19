package com.fedicode.getticket.repository;

import com.fedicode.getticket.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin,Long> {

    Optional<Admin> findByEmail(String email);

    List<Admin> findByOrganizationNameContainingAndOrganizationLocationContaining(String name, String location);
}
