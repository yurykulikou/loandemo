package com.demo.loan.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanRequest {
    private double loanAmount; // Total loan amount
    private double interestRate; // Annual interest rate in percent (e.g. 5.5)
    private int termMonths; // Loan term in months
}