package it.laskaridis.payments.clearing.model;

import it.laskaridis.payments.errors.model.ResourceNotFoundException;

/**
 * Expected to be thrown when the code violates the assumption that a {@link ClearingCost}
 * instance for the specified card issuing country must exist.
 */
public class ClearingCostNotFoundException extends ResourceNotFoundException {
    public ClearingCostNotFoundException(final String cardIssuingCountry) {
        super(ClearingCost.class.getSimpleName(), String.format(
                "Could not find a clearing cost entry for %s", cardIssuingCountry));
    }
}
