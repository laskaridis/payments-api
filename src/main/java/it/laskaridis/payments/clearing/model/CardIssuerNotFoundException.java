package it.laskaridis.payments.clearing.model;

import it.laskaridis.payments.errors.model.ResourceNotFoundException;

public class CardIssuerNotFoundException extends ResourceNotFoundException {
    public CardIssuerNotFoundException(String iin) {
        super(CardIssuer.class.getSimpleName(), String.format(
                "Card issuer #%s was not found.", iin));
    }
}
