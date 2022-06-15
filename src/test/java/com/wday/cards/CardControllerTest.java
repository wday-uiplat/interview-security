package com.wday.cards;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class CardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void fetchCreditCardAuthorized()
            throws Exception {
        this.mockMvc.perform(get("/cards/credit")
                        .header(HttpHeaders.AUTHORIZATION, "YWRtaW46YWRtaW4="))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(CreditCardController.CREDIT_CARD)));
    }

    @Test
    void fetchCreditCardUnAuthorized()
            throws Exception {
        this.mockMvc.perform(get("/cards/credit")
                        .header(HttpHeaders.AUTHORIZATION, "someinvalid="))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(containsString("Unauthorized access")));
    }

    @Test
    void fetchDebitCardAuthorized()
            throws Exception {
        this.mockMvc.perform(get("/cards/debit")
                        .header(HttpHeaders.AUTHORIZATION, "YWRtaW46YWRtaW4="))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(DebitCardController.DEBIT_CARD)));
    }

    @Test
    void fetchDebitCardUnAuthorized()
            throws Exception {
        this.mockMvc.perform(get("/cards/debit")
                        .header(HttpHeaders.AUTHORIZATION, "someinvalid="))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(containsString("Unauthorized access")));
    }
}
