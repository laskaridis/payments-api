package it.laskaridis.payments.clearing.model;

import it.laskaridis.payments.errors.model.ResourceAlreadyTakenException;

public final class CardIssuerAlreadyExistsException extends ResourceAlreadyTakenException {
    public CardIssuerAlreadyExistsException(final String iin) {
        super(CardIssuer.class.getSimpleName(), String.format(
                "Card issuer #%s already exists.", iin));
    }
}
