package com.fedicode.getticket.ControllerTy;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeControllerTy {

    @GetMapping("/")
    public String homePage() {
        return "pages/home";
    }

    @GetMapping("/home")
    public String home() {
        return "pages/home";
    }
}
