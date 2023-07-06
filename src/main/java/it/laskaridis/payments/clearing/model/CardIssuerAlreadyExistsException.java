package it.laskaridis.payments.clearing.model;

import it.laskaridis.payments.errors.model.ResourceAlreadyTakenException;

public class CardIssuerAlreadyExistsException extends ResourceAlreadyTakenException {
    public CardIssuerAlreadyExistsException(String iin) {
        super(CardIssuer.class.getSimpleName(), String.format(
                "Card issuer #%s already exists.", iin));
    }
}
