package it.laskaridis.payments.common.model;

import it.laskaridis.payments.utils.Immutable;
import it.laskaridis.payments.utils.ValueObject;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Encapsulates a monetary amount.
 *
 * @param amount the amount value
 * @param currency the amount currency
 */
@ValueObject
@Immutable
public record Money(BigDecimal amount, String currency) {

    public static Money of(final double amount, final String currency) {
        return new Money(amount, currency);
    }

    public Money(final BigDecimal amount, final String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public Money(final Double amount, final String currency) {
        this(BigDecimal.valueOf(amount), currency);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Objects.equals(amount, money.amount) && Objects.equals(currency, money.currency);
    }

    @Override
    public String toString() {
        return "Money{" +
                "amount=" + amount +
                ", currency=" + currency +
                '}';
    }
}
