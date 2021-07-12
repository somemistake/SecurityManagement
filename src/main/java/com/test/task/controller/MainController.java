package com.test.task.controller;

import com.test.task.service.SecurityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {
    private final SecurityService securityService;

    public MainController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @GetMapping
    public String getAllSecid(Model model) {
        model.addAttribute("securities", securityService.findAll());
        return "index";
    }
}
