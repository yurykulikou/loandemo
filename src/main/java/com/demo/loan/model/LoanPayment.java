package com.demo.loan.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoanPayment {
    private int month;              // Month number (1, 2, ..., n)
    private double totalPayment;    // Total monthly payment
    private double principal;       // Principal part of the payment - base (totalPayment - interest)
    private double interest;        // Interest part of the payment - %
    private double remainingBalance;// Remaining balance after payment
}
