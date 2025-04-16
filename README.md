Alex Kryzanovsky <alexander.kryzanovsky@timvero.com>

Build a SPRING BOOT application to calculate loan schedule with annuity payments by inputting loan amount, interest rate and term.
Свое решение можете залить в свой репозиторий и предоставить ссылку.
------------------------------------------------------------------------
1. Task 

Create a Spring Boot application (REST API) that:
- Accepts loan amount, interest rate, and loan term (in months or years);
- Calculates an annuity payment schedule;
- Returns a list of monthly payments with details: month number, total payment, interest, principal, and remaining balance.
-----------------------------------------------------------------------
2. Solution

Rest API get:
LoanRequest 
double loanAmount; // Total loan amount
double interestRate; // % - Annual interest rate (e.g. 5.5)
int termMonths; // Loan term in months

example:
{
  "loanAmount": 100000,
  "interestRate": 10,
  "termMonths": 12
}

Returns:
LoanPayment // list of
int month; // Month number (1, 2, ..., n)
double totalPayment; // Total monthly payment
double principal; // Principal part of the payment
double interest; // Interest part of the payment
double remainingBalance; // Remaining balance after payment

Service: Formula:
A = P * [r*(1+r)^n] / [(1+r)^n - 1]

P - loan amount - total

r - monthly interest rate (1% = 0.01)

n - months count (loan term)

-----------------------------------------------------------------------
3. Design

Spring Boot, Spring Web (REST API), JSON, Maven, Git

Structure
----------------------------
app:
LoanScheduleApplication

controller:
LoanController - REST service "/api/loan/schedule"

model:
LoanRequest
LoanPayment

service:
LoanService - calculates and returns loan schedule
List<LoanPayment> calculateSchedule(LoanRequest request)

test:
LoanApplicationTest
LoanServiceTest 

--------------------------------------------------------

4. Tests

Service:
testSchedule_Case1 // happy path 
in: 600,12,6
out: 103.37,620.22,20.22

testSchedule_Case2 // happy path 
in: 100000,10,3
out: 8791.59,833.33,7958.26,92041.74

testSchedule_Case3 // happy path 
in: 1200,0,48
out: 100.00,1200.00,0.00

testSchedule_Case4 // Exception 
in: any wrong
out: {error: <error description>}

App: Test Exception processing
+ Manual test using PostMan
--------------------------------------------------------
