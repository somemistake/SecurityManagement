package com.test.task.controller;

import com.test.task.model.Security;
import com.test.task.service.SecurityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/security")
public class SecurityController {
    private final SecurityService securityService;

    public SecurityController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @GetMapping
    public String getSecuritiesTable(Model model) {
        model.addAttribute("securities", securityService.findAll());
        return "security/securityTable";
    }

    @GetMapping("/createSecurityForm")
    public String getCreateSecurityForm(@ModelAttribute("security") Security security) {
        return "security/createSecurityForm";
    }

    @PostMapping("/createSecurity")
    public String createSecurity(@ModelAttribute("security") Security security) {
        securityService.save(security);
        return "redirect:/security";
    }

    @GetMapping("/editSecurityForm/{secid}")
    public String getEditSecurityForm(@PathVariable("secid") String secid,
                                        Model model) {
        model.addAttribute("security", securityService.findBySecid(secid).get());
        return "security/editSecurityForm";
    }

    @PostMapping("/editSecurity")
    public String editSecurity(@ModelAttribute("security") Security security) {
        securityService.save(security);
        return "redirect:/security";
    }

    @GetMapping("/deleteSecurity/{secid}")
    public String deleteSecurity(@PathVariable("secid") String secid) {
        securityService.deleteBySecid(secid);
        return "redirect:/security";
    }
}
