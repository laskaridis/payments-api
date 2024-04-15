package it.laskaridis.payments.clearing.model;

import it.laskaridis.payments.errors.model.ResourceNotFoundException;

public final class CardIssuerNotFoundException extends ResourceNotFoundException {
    public CardIssuerNotFoundException(final String iin) {
        super(CardIssuer.class.getSimpleName(), String.format(
                "Card issuer #%s was not found.", iin));
    }
}
