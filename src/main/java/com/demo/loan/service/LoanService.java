package com.demo.loan.service;

import com.demo.loan.model.LoanPayment;
import com.demo.loan.model.LoanRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoanService {
    public List<LoanPayment> calculateSchedule(LoanRequest request) {

        if (request == null) throw new IllegalArgumentException("request is null");
        if (request.getLoanAmount() <= 0) throw new IllegalArgumentException("Loan amount must be greater than 0");
        if (request.getTermMonths() <= 1)
            throw new IllegalArgumentException("Loan term in months must be greater than 1");

        List<LoanPayment> schedule = new ArrayList<>();

        double principal = request.getLoanAmount();
        double interestRate = request.getInterestRate();
        int termMonths = request.getTermMonths();

        double roundTotalPrincipal = 0;

        double monthlyRate = interestRate / 12 / 100;
        double annuityPayment = calculateAnnuityPayment(principal, monthlyRate, termMonths);

        double balance = principal;

        // Calculate LoanPayment for each month
        for (int month = 1; month <= termMonths; month++) {
            double interestPart = monthlyRate * balance;
            double principalPart = annuityPayment - interestPart;
            balance -= principalPart;

            roundTotalPrincipal += round(principalPart);

            if (month == termMonths) {
                // Fix rounding issues on last payment
                principalPart += principal - roundTotalPrincipal;
                balance = 0;
            }

            schedule.add(new LoanPayment(
                    month,
                    round(annuityPayment),
                    round(principalPart),
                    round(interestPart),
                    round(balance)
            ));

        }
        return schedule;
    }

    // A = P * [r*(1+r)^t] / [(1+r)^t - 1]
    private double calculateAnnuityPayment(double principal, double rate, int term) {

        // Calculate the fixed annuity monthly payment
        double annuityPayment = (rate == 0)
                ? principal / term
                : principal * (rate * Math.pow(1 + rate, term)) /
                (Math.pow(1 + rate, term) - 1);
        return annuityPayment;

    }

    // Rounds to 2 decimal places for clean output.
    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
