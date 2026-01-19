package com.fedicode.getticket.ControllerTy;

import com.fedicode.getticket.entity.Admin;
import com.fedicode.getticket.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrganizationControllerTy {

    private final AdminService adminService;

    @GetMapping("/organization")
    public String listOrganizations(@RequestParam(required = false) String name,
                                    @RequestParam(required = false) String location,
                                    Model model) {
        List<Admin> organizations;
        
        if ((name != null && !name.isEmpty()) || (location != null && !location.isEmpty())) {
            organizations = adminService.searchOrganizationByNameAndLocation(
                    name != null ? name : "", 
                    location != null ? location : ""
            );
        } else {
            organizations = adminService.getAllOrganizations();
        }
        
        model.addAttribute("organizations", organizations);
        model.addAttribute("searchName", name);
        model.addAttribute("searchLocation", location);
        return "pages/organizations";
    }

    @GetMapping("/organization/{id}")
    public String organizationDetails(@PathVariable Long id, Model model) {
        Admin organization = adminService.getOrganizationDetails(id);
        model.addAttribute("organization", organization);
        return "pages/organization-details";
    }
}
