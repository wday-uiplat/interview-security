package com.wday.cards;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class DebitCardController {

    static final String DEBIT_CARD = "1000-1000-1000-1000";

    @GetMapping("/cards/debit")
    public String getDebitCard(HttpServletRequest request, HttpServletResponse response) {
        if (!isRequestAuthorized(request)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return "Unauthorized access.";
        }
        return DEBIT_CARD;
    }

    private boolean isRequestAuthorized(HttpServletRequest request) {
        boolean isAuthorized = false;

        String base64AuthToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (base64AuthToken != null && !base64AuthToken.isBlank()) {
            byte[] credentials = Base64.getDecoder().decode(base64AuthToken);
            String auth = new String(credentials, StandardCharsets.UTF_8);
            String[] authValues = auth.split(":", 2);

            if (authValues.length != 0) {
                isAuthorized = true;
            }
        }

        return isAuthorized;
    }
}
