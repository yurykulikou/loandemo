package com.demo.loan.controller;

import com.demo.loan.model.LoanPayment;
import com.demo.loan.model.LoanRequest;
import com.demo.loan.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/loan")
public class LoanController {
    private final LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping("/schedule")
    public List<LoanPayment> calculateSchedule(@RequestBody LoanRequest request) {
        return loanService.calculateSchedule(request);
    }
}
