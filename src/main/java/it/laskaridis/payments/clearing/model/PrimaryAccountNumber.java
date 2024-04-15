package it.laskaridis.payments.clearing.model;

import it.laskaridis.payments.utils.Immutable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Immutable
@RequiredArgsConstructor
@EqualsAndHashCode
public final class PrimaryAccountNumber {

    private static final String EIGHT_TO_NINETEEN_DIGITS = "^[0-9]{8,19}$";

    @Pattern(regexp = EIGHT_TO_NINETEEN_DIGITS)
    @NotNull
    private final String number;

    public String getIssuerIdentificationNumber() {
        return this.number.substring(0, 6);
    }

    public String getAccountIdentificationNumber() {
        return this.number.substring(6, this.number.length());
    }

    @Override
    public String toString() {
        return this.number;
    }
}
