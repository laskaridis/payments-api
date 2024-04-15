package it.laskaridis.payments.clearing.model;

import it.laskaridis.payments.errors.model.ResourceNotFoundException;

public final class CreditCardDetailsNotFoundException extends ResourceNotFoundException {
    public CreditCardDetailsNotFoundException(final PrimaryAccountNumber number) {
        super(CardIssuer.class.getSimpleName(), String.format(
                "Details of credit card with PAN %s could not be retrieved.", number.toString()));
    }
}
