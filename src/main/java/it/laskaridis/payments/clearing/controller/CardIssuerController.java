package it.laskaridis.payments.clearing.controller;

import it.laskaridis.payments.clearing.model.CardIssuerNotFoundException;
import it.laskaridis.payments.clearing.model.CardIssuerService;
import it.laskaridis.payments.clearing.view.json.CardIssuerView;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles client requests for {@link it.laskaridis.payments.clearing.model.CardIssuer}
 * resources.
 */
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
class CardIssuerController {

    private final CardIssuerService service;

    @GetMapping("/card_issuer/{iin}")
    CardIssuerView show(@PathVariable final String iin) {
        return this.service.getCardIssuer(iin)
                .map(CardIssuerView::new)
                .orElseThrow(() -> new CardIssuerNotFoundException(iin));
    }
}
