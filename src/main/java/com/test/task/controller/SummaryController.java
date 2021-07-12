package com.test.task.controller;

import com.test.task.service.SummaryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/summary")
public class SummaryController {
    private final SummaryService summaryService;

    public SummaryController(SummaryService summaryService) {
        this.summaryService = summaryService;
    }

    @GetMapping("/{secid}")
    public String getSummaryTable(@PathVariable("secid") String secid,
                                  Model model) {
        model.addAttribute("summaries", summaryService.getAllBySecid(secid));
        return "summary/summaryTable";
    }
}
