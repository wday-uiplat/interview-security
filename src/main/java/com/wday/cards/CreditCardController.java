package com.wday.cards;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class CreditCardController {

    static final String CREDIT_CARD = "2000-2000-2000-2000";

    @GetMapping("/cards/credit")
    public String getCreditCard() {
        return CREDIT_CARD;
    }
}
