package com.test.task.controller;

import com.test.task.model.History;
import com.test.task.service.HistoryService;
import com.test.task.service.SecurityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@Controller
@RequestMapping("/history")
public class HistoryController {
    private final HistoryService historyService;
    private final SecurityService securityService;

    public HistoryController(HistoryService historyService, SecurityService securityService) {
        this.historyService = historyService;
        this.securityService = securityService;
    }

    @GetMapping
    public String getHistoriesTable(Model model) {
        model.addAttribute("histories", historyService.findAll());
        return "history/historyTable";
    }

    @GetMapping("/createHistoryForm")
    public String getCreateHistoryForm(@ModelAttribute("history") History history,
                                       Model model) {
        model.addAttribute("securities", securityService.findAll());
        return "history/createHistoryForm";
    }

    @PostMapping("/createHistory")
    public String createHistory(@ModelAttribute("history") History history,
                                @ModelAttribute("secid") String secid,
                                @ModelAttribute("tradedate") String tradedate) {
        history.setSecurity(securityService.findBySecid(secid).get());
        history.setTradedate(Date.valueOf(tradedate));
        historyService.save(history);
        return "redirect:/history";
    }

    @GetMapping("/editHistoryForm/{id}")
    public String getEditHistoryForm(@PathVariable("id") long id,
                                      Model model) {
        model.addAttribute("history", historyService.findById(id).get());
        model.addAttribute("securities", securityService.findAll());
        return "history/editHistoryForm";
    }

    @PostMapping("/editHistory")
    public String editHistory(@ModelAttribute("history") History history,
                              @ModelAttribute("secid") String secid,
                              @ModelAttribute("tradedate") String tradedate) {
        history.setSecurity(securityService.findBySecid(secid).get());
        history.setTradedate(Date.valueOf(tradedate));
        historyService.save(history);
        return "redirect:/history";
    }

    @GetMapping("/deleteHistory/{id}")
    public String deleteHistory(@PathVariable("id") long id) {
        historyService.deleteById(id);
        return "redirect:/history";
    }
}
