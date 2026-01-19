package com.fedicode.getticket.ControllerTy;

import com.fedicode.getticket.dto.AdminSignUpRequest;
import com.fedicode.getticket.dto.ClientSignupRequest;
import com.fedicode.getticket.entity.Admin;
import com.fedicode.getticket.entity.Client;
import com.fedicode.getticket.repository.AdminRepository;
import com.fedicode.getticket.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AuthControllerTy {

    private final AdminRepository adminRepository;
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                           @RequestParam(value = "logout", required = false) String logout,
                           Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid username or password");
        }
        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully");
        }
        return "auth/login";
    }

    @GetMapping("/sign-up")
    public String signUpPage() {
        return "auth/sign-up";
    }

    @GetMapping("/signup/admin")
    public String adminSignUpPage(Model model) {
        model.addAttribute("adminSignUpRequest", new AdminSignUpRequest());
        return "auth/admin-signup";
    }

    @GetMapping("/signup/client")
    public String clientSignUpPage(Model model) {
        model.addAttribute("clientSignupRequest", new ClientSignupRequest());
        return "auth/client-signup";
    }

    @PostMapping("/signup/admin")
    public String adminSignUp(@ModelAttribute AdminSignUpRequest req, RedirectAttributes redirectAttributes) {
        if (req.getEmail() == null || req.getPassword() == null) {
            redirectAttributes.addFlashAttribute("error", "Email and password are required");
            return "redirect:/signup/admin";
        }
        if (adminRepository.findByEmail(req.getEmail()).isPresent()) {
            redirectAttributes.addFlashAttribute("error", "An account with this email already exists");
            return "redirect:/signup/admin";
        }
        
        Admin admin = new Admin();
        admin.setEmail(req.getEmail());
        admin.setPasswordHash(passwordEncoder.encode(req.getPassword()));
        admin.setOrganizationName(req.getOrganizationName());
        admin.setOrganizationLocation(req.getOrganizationLocation());
        admin.setOrganizationCode(req.getOrganizationCode());
        admin.setPhone(req.getPhone());
        admin.setCurrentNumber(0);
        adminRepository.save(admin);
        
        redirectAttributes.addFlashAttribute("success", "Account created successfully! Please login.");
        return "redirect:/login";
    }

    @PostMapping("/signup/client")
    public String clientSignUp(@ModelAttribute ClientSignupRequest req, RedirectAttributes redirectAttributes) {
        if (req.getUsername() == null || req.getPassword() == null) {
            redirectAttributes.addFlashAttribute("error", "Username and password are required");
            return "redirect:/signup/client";
        }
        if (clientRepository.findByUsername(req.getUsername()).isPresent()) {
            redirectAttributes.addFlashAttribute("error", "An account with this username already exists");
            return "redirect:/signup/client";
        }
        
        Client client = new Client();
        client.setUsername(req.getUsername());
        client.setNom(req.getNom());
        client.setPrenom(req.getPrenom());
        client.setPasswordHash(passwordEncoder.encode(req.getPassword()));
        clientRepository.save(client);
        
        redirectAttributes.addFlashAttribute("success", "Account created successfully! Please login.");
        return "redirect:/login";
    }
}
