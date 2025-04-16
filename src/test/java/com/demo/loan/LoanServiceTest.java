package com.demo.loan;

import com.demo.loan.model.LoanPayment;
import com.demo.loan.model.LoanRequest;
import com.demo.loan.service.LoanService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@AutoConfigureMockMvc
class LoanServiceTest {

    private final LoanService loanService = new LoanService();

    @Test
    public void testSchedule_Case1() {
        LoanRequest request = new LoanRequest(100000, 10, 12);
        List<LoanPayment> schedule = loanService.calculateSchedule(request);

        assertEquals(12, schedule.size());

        assertEquals(1, schedule.get(0).getMonth());
        assertEquals(8791.59, schedule.get(0).getTotalPayment());
        assertEquals(833.33, schedule.get(0).getInterest());
        assertEquals(7958.26, schedule.get(0).getPrincipal());
        assertEquals(92041.74, schedule.get(0).getRemainingBalance());

        assertEquals(12, schedule.get(11).getMonth());
        assertEquals(8791.59, schedule.get(11).getTotalPayment());
        assertEquals(72.66, schedule.get(11).getInterest());
        assertEquals(8718.94, schedule.get(11).getPrincipal());
        assertEquals(0.0, schedule.get(11).getRemainingBalance());
    }

    @Test
    public void testSchedule_Case2() {
        LoanRequest request = new LoanRequest(600, 12, 6);
        List<LoanPayment> schedule = loanService.calculateSchedule(request);

        assertEquals(6, schedule.size());

        assertEquals(1, schedule.get(0).getMonth());
        assertEquals(103.53, schedule.get(0).getTotalPayment());
        assertEquals(6.0, schedule.get(0).getInterest());
        assertEquals(97.53, schedule.get(0).getPrincipal());
        assertEquals(502.47, schedule.get(0).getRemainingBalance());

        assertEquals(6, schedule.get(5).getMonth());
        assertEquals(103.53, schedule.get(5).getTotalPayment());
        assertEquals(1.03, schedule.get(5).getInterest());
        assertEquals(102.51, schedule.get(5).getPrincipal());
        assertEquals(0.0, schedule.get(5).getRemainingBalance());
    }

    @Test
    public void testSchedule_Case3() {
        LoanRequest request = new LoanRequest(1200, 0, 48);
        List<LoanPayment> schedule = loanService.calculateSchedule(request);

        assertEquals(48, schedule.size());

        assertEquals(1, schedule.get(0).getMonth());
        assertEquals(25, schedule.get(0).getTotalPayment());
        assertEquals(0.0, schedule.get(0).getInterest());
        assertEquals(25, schedule.get(0).getPrincipal());
        assertEquals(1175.0, schedule.get(0).getRemainingBalance());

        assertEquals(48, schedule.get(47).getMonth());
        assertEquals(25, schedule.get(47).getTotalPayment());
        assertEquals(0.0, schedule.get(47).getInterest());
        assertEquals(25, schedule.get(47).getPrincipal());
        assertEquals(0.0, schedule.get(47).getRemainingBalance());
    }

    @Test
    public void testSchedule_Case4() {

        // Test Exceptions
        assertThrows(IllegalArgumentException.class, () -> {
            loanService.calculateSchedule(new LoanRequest(0, 2, 2));
        });

        assertThrows(IllegalArgumentException.class, () -> {
            loanService.calculateSchedule(new LoanRequest(1000, 0, 1));
        });

        assertThrows(IllegalArgumentException.class, () -> {
            loanService.calculateSchedule(null);
        });

    }
}
