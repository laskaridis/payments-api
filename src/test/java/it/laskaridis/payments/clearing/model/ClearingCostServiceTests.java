package it.laskaridis.payments.clearing.model;

import it.laskaridis.payments.bintable.BintableClient;
import it.laskaridis.payments.bintable.BintableClientException;
import it.laskaridis.payments.bintable.BintableData;
import it.laskaridis.payments.bintable.BintableDataCountry;
import it.laskaridis.payments.clearing.ClearingCostTestFixtures;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClearingCostServiceTests {

    @Mock
    private ClearingCostRepository clearingCosts;

    @Mock
    private CardIssuerService creditCards;

    @Mock
    private BintableClient bintableClient;

    private ClearingCostService subject;

    private ClearingCostTestFixtures fixtures = new ClearingCostTestFixtures();

    @BeforeEach
    public void beforeAll() {
        this.subject = new ClearingCostService(
                this.clearingCosts,
                this.creditCards,
                this.bintableClient);
    }

    @Test
    public void shouldReturnClearingCostForCreditCard() throws BintableClientException {
        var number = new PrimaryAccountNumber("1234567890");
        var iin = number.getIssuerIdentificationNumber();
        var data = buildBintableResponseDataWithCountry("at");
        var cost = this.fixtures.buildClearingCostWith("at");

        when(this.creditCards.getCardIssuer(iin))
                .thenReturn(Optional.empty());
        when(this.bintableClient.getCreditCardDetails(iin))
                .thenReturn(data);
        when(this.clearingCosts.findByCardIssuingCountry("at"))
                .thenReturn(Optional.of(cost));

        var actual = this.subject.getClearingCost(number);
        assertThat(actual.getClearingCostCurrency()).isEqualTo(cost.getClearingCostCurrency());
        assertThat(actual.getClearingCostAmount()).isEqualTo(cost.getClearingCostAmount());
    }

    @Test
    public void shouldNotReturnClearingCostForCreditCard_whenCardDetailsCannotBeFound() throws BintableClientException {
        var number = new PrimaryAccountNumber("1234567890");

        when(this.bintableClient.getCreditCardDetails(number.toString()))
                .thenThrow(new BintableClientException("error"));

        assertThrows(CreditCardDetailsNotFoundException.class, () ->
                this.subject.getClearingCost(number));
    }

    @Test
    public void shouldNotReturnClearingCostForCreditCard_whenClearingCostCannotBeFound() throws BintableClientException {
        var number = new PrimaryAccountNumber("1234567890");
        var card = buildBintableResponseDataWithCountry("at");

        when(this.creditCards.getCardIssuer(number.getIssuerIdentificationNumber()))
                .thenReturn(Optional.empty());
        when(this.bintableClient.getCreditCardDetails(number.getIssuerIdentificationNumber()))
                .thenReturn(card);
        when(this.clearingCosts.findByCardIssuingCountry("at"))
                .thenReturn(Optional.empty());

        assertThrows(ClearingCostNotFoundException.class, () ->
                this.subject.getClearingCost(number));
    }

    @Test
    public void shouldNotReturnClearingCostForCreditCard_whenCreditCardNumberInvalid() {
        var number = new PrimaryAccountNumber("invalid");
        assertThrows(ConstraintViolationException.class, () ->
                this.subject.getClearingCost(number));
    }

    // fixtures

    private static BintableData buildBintableResponseDataWithCountry(String countryCode) {
        var country = new BintableDataCountry();
        country.setCode(countryCode);
        var card = new BintableData();
        card.setCountry(country);
        return card;
    }

}
