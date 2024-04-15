package it.laskaridis.payments.clearing.model;

import it.laskaridis.payments.errors.model.ResourceAlreadyTakenException;

/**
 * Expected to be thrown when the code violates the assumption that a {@link ClearingCost}
 * instance for the specified card issuing country should not exist.
 */
public final class ClearingCostAlreadyExistsException extends ResourceAlreadyTakenException {
    public ClearingCostAlreadyExistsException(final String cardIssuingCountry) {
        super(ClearingCost.class.getSimpleName(), String.format(
                "Clearing cost already exists for %s", cardIssuingCountry));
    }
}
