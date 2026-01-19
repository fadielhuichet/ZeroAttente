package com.fedicode.getticket.controller;

import com.fedicode.getticket.dto.AdminSignUpRequest;
import com.fedicode.getticket.dto.AdminUpdateRequest;
import com.fedicode.getticket.entity.Admin;
import com.fedicode.getticket.repository.AdminRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/auth")
@AllArgsConstructor
public class AdminAuthController {
    private AdminRepository adminRepository;
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody AdminSignUpRequest req){
        if (req.getEmail() == null || req.getPassword() == null) {
            return ResponseEntity.badRequest().body("email and password required");
        }
        if (adminRepository.findByEmail(req.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
        }
        Admin a=new Admin();
        a.setEmail(req.getEmail());
        a.setPasswordHash(passwordEncoder.encode(req.getPassword()));
        a.setOrganizationName(req.getOrganizationName());
        a.setOrganizationLocation(req.getOrganizationLocation());
        a.setOrganizationCode(req.getOrganizationCode());
        a.setPhone(req.getPhone());
        adminRepository.save(a);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@AuthenticationPrincipal UserDetails userDetails,
                                           @RequestBody AdminUpdateRequest req) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not authenticated");
        }

        Admin admin = adminRepository.findByEmail(userDetails.getUsername()).orElse(null);
        if (admin == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Admin not found");
        }

        if (req.getOrganizationName() != null && !req.getOrganizationName().isEmpty()) {
            admin.setOrganizationName(req.getOrganizationName());
        }
        if (req.getOrganizationLocation() != null && !req.getOrganizationLocation().isEmpty()) {
            admin.setOrganizationLocation(req.getOrganizationLocation());
        }
        if (req.getPhone() != null) {
            admin.setPhone(req.getPhone());
        }

        if (req.getNewPassword() != null && !req.getNewPassword().isEmpty()) {
            if (req.getCurrentPassword() == null ||
                !passwordEncoder.matches(req.getCurrentPassword(), admin.getPasswordHash())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Current password is incorrect");
            }
            admin.setPasswordHash(passwordEncoder.encode(req.getNewPassword()));
        }

        adminRepository.save(admin);
        return ResponseEntity.ok(admin);
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not authenticated");
        }
        Admin admin = adminRepository.findByEmail(userDetails.getUsername()).orElse(null);
        if (admin == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Admin not found");
        }
        return ResponseEntity.ok(admin);
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not authenticated");
        }
        Admin admin = adminRepository.findByEmail(userDetails.getUsername()).orElse(null);
        if (admin == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Admin not found");
        }
        return ResponseEntity.ok(admin);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        return ResponseEntity.ok().build();
    }
}
