package it.laskaridis.payments.clearing.controller;

import it.laskaridis.payments.clearing.model.ClearingCost;
import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

public class ClearingCostResourceTests {

    @Test
    public void shouldReturnResourceLocation() {
        // Given
        final var cost = new ClearingCost();
        cost.setCardIssuingCountry("gr");
        final var subject = new ClearingCostResource(cost);

        // When
        final var location = subject.getLocation();

        // Then
        assertThat(location.toString()).isEqualTo("/countries/gr/clearing_cost");
    }
}
