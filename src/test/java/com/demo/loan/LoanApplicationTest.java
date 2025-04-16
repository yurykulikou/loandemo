package com.demo.loan;

import com.demo.loan.model.LoanRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class LoanApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testLoanScheduleCall() throws Exception {
        LoanRequest request = new LoanRequest(1000.0, 12.0, 2);

        mockMvc.perform(post("/api/loan/schedule")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].month", is(1)))
                .andExpect(jsonPath("$[0].totalPayment").isNumber())
                .andExpect(jsonPath("$[1].month", is(2)))
                .andExpect(jsonPath("$[1].remainingBalance", is(0.0)));
    }

    @Test
    void testLoanScheduleException() throws Exception {
        LoanRequest request = new LoanRequest(0.0, 12.0, 2);

        mockMvc.perform(post("/api/loan/schedule")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest()) // Must be = 400
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Loan amount must be greater than 0")));
    }
}
