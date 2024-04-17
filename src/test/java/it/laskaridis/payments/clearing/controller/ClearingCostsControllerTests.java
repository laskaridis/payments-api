package it.laskaridis.payments.clearing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.laskaridis.payments.IntegrationTest;
import it.laskaridis.payments.bintable.BintableClient;
import it.laskaridis.payments.bintable.BintableClientException;
import it.laskaridis.payments.bintable.BintableData;
import it.laskaridis.payments.bintable.BintableDataCountry;
import it.laskaridis.payments.clearing.ClearingCostTestFixtures;
import it.laskaridis.payments.clearing.model.*;
import it.laskaridis.payments.clearing.view.json.CardNumberForm;
import it.laskaridis.payments.clearing.view.json.ClearingCostForm;
import it.laskaridis.payments.common.model.Money;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@IntegrationTest
@WithMockUser
public class ClearingCostsControllerTests {

    @Autowired
    private MockMvc api;

    @MockBean
    private BintableClient creditCardLookupClient;

    @Autowired
    private ObjectMapper json;

    @Autowired
    private ClearingCostRepository costs;

    @Autowired
    private ClearingCostTestFixtures fixtures;

    @Test
    public void shouldCreateClearingCost_WhenNotExist() throws Exception {
        // Given
        final var country = "gr";
        final var form = new ClearingCostForm(Money.of(200.0, "EUR"));

        // When
        final var response = this.api.perform(post("/api/v1/countries/{code}/clearing_cost", country)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .content(this.json.writeValueAsString(form)));

        // Then
        final var actual = this.costs.findByCardIssuingCountry(country).get();
        assertThat(actual.getCardIssuingCountry()).isEqualTo(country);
        assertThat(actual.getClearingCostCurrency()).isEqualTo(form.getClearingCostCurrency());
        assertThat(actual.getClearingCostAmount()).isEqualTo(form.getClearingCostAmount());

        final var location = new ClearingCostResource(actual).getLocation();
        response.andExpect(status().isCreated())
            .andExpect(header().stringValues("Location", location.toString()))
            .andExpect(jsonPath("$.id").value(actual.getId().toString()))
            .andExpect(jsonPath("$.version").exists())
            .andExpect(jsonPath("$.created_at").exists())
            .andExpect(jsonPath("$.last_modified_at").exists())
            .andExpect(jsonPath("$.card_issuing_country").value(actual.getCardIssuingCountry()))
            .andExpect(jsonPath("$.clearing_cost_amount").value(actual.getClearingCostAmount().toString()))
            .andExpect(jsonPath("$.clearing_cost_currency").value(actual.getClearingCostCurrency()));
    }

    @Test
    public void shouldNotCreateClearingCost_WhenAlreadyPresent() throws Exception {
        final var anExistingClearingCost = this.fixtures.createClearingCost();
        final var country = anExistingClearingCost.getCardIssuingCountry();

        this.api.perform(post("/api/v1/countries/{code}/clearing_cost", country)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .content(this.json.writeValueAsString(anExistingClearingCost)))
            .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldUpdateClearingCost_WhenFound() throws Exception {
        // Given
        final var expectedCost = this.fixtures.createClearingCostWith("gr", "USD", 100.0);
        final var country = expectedCost.getCardIssuingCountry();
        final var form = new ClearingCostForm(Money.of(200.0, "EUR"));

        // When
        final var response = this.api.perform(put("/api/v1/countries/{code}/clearing_cost", country)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .content(this.json.writeValueAsString(form)));

        // Then
        final var actualCost = this.costs.findById(expectedCost.getId()).get();
        assertThat(actualCost.getClearingCostAmount()).isEqualTo(form.getClearingCostAmount());
        assertThat(actualCost.getClearingCostCurrency()).isEqualTo(form.getClearingCostCurrency());

        response.andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(actualCost.getId().toString()))
            .andExpect(jsonPath("$.version").exists())
            .andExpect(jsonPath("$.created_at").exists())
            .andExpect(jsonPath("$.last_modified_at").exists())
            .andExpect(jsonPath("$.card_issuing_country").value(country))
            .andExpect(jsonPath("$.clearing_cost_amount").value(form.getClearingCostAmount().toString()))
            .andExpect(jsonPath("$.clearing_cost_currency").value(form.getClearingCostCurrency()));
    }

    @Test
    public void shouldNotUpdateClearingCost_WhenNotFound() throws Exception {
        final var anInvalidCountry = "xx";
        final var form = new ClearingCostForm(Money.of(200.0, "EUR"));

        this.api.perform(put("/api/v1/countries/{code}/clearing_cost", anInvalidCountry)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .content(this.json.writeValueAsString(form)))
            .andExpect(status().isNotFound());
    }

    @Test
    public void shouldDeleteClearingCost_WhenFound() throws Exception {
        // Given
        final var cost = this.fixtures.createClearingCostWith("gr", "USD", 100.0);
        final var country = cost.getCardIssuingCountry();

        // When
        this.api.perform(delete("/api/v1/countries/{code}/clearing_cost", country)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Then
        final var actual = this.costs.findById(cost.getId());
        assertThat(actual).isEmpty();
    }

    @Test
    public void shouldNotDeleteClearingCost_WhenNotFound() throws Exception {
        final var anInvalidCountry = "xx";

        this.api.perform(delete("/api/v1/countries/{code}/clearing_cost", anInvalidCountry)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    public void shouldRetrieveClearingCost_WhenFound() throws Exception {
        final var cost = this.fixtures.createClearingCostWith("uk", "USD", 100.0);
        final var country = cost.getCardIssuingCountry();

        this.api.perform(get("/api/v1/countries/{code}/clearing_cost", country)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(header().exists("ETag"))
            .andExpect(jsonPath("$.id").value(cost.getId().toString()))
            .andExpect(jsonPath("$.version").exists())
            .andExpect(jsonPath("$.created_at").exists())
            .andExpect(jsonPath("$.last_modified_at").exists())
            .andExpect(jsonPath("$.card_issuing_country").value(cost.getCardIssuingCountry()))
            .andExpect(jsonPath("$.clearing_cost_amount").value(cost.getClearingCostAmount().doubleValue()))
            .andExpect(jsonPath("$.clearing_cost_currency").value(cost.getClearingCostCurrency()));
    }

    @Test
    public void shouldNotRetrieveClearingCost_WhenNotFound() throws Exception {
        final var anInvalidCountry = "xx";

        this.api.perform(get("/api/v1/countries/{code}/clearing_cost", anInvalidCountry)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    public void shouldRetrieveClearingCostByCreditCardNumber_whenCreditCardFound() throws Exception {
        final var card = buildCreditCardLookupResponseWithCounty("de");
        final var cost = this.fixtures.createClearingCostWith("de", "EUR", 100.0);
        final var form = new CardNumberForm();
        form.setCardNumber("1234567890");

        when(this.creditCardLookupClient
                .getCreditCardDetails(form.toModel().getIssuerIdentificationNumber()))
                .thenReturn(card);

        this.api.perform(post("/api/v1/payment-cards-cost")
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .content(this.json.writeValueAsString(form)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.country").value(cost.getCardIssuingCountry()))
            .andExpect(jsonPath("$.cost").value(cost.getClearingCostAmount()));
    }

    @Test
    public void shouldNotRetrieveClearingCostByCreditCardNumber_whenCreditCardNotFound() throws Exception {
        final var form = new CardNumberForm();
        form.setCardNumber("1234567890");

        when(this.creditCardLookupClient
                .getCreditCardDetails(form.toModel().getIssuerIdentificationNumber()))
                .thenThrow(new BintableClientException("error"));

        this.api.perform(post("/api/v1/payment-cards-cost")
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .content(this.json.writeValueAsString(form)))
            .andExpect(status().isNotFound());
    }

    @Test
    public void shouldNotRetrieveClearingCostByCreditCardNumber_whenClearingCostNotFound() throws Exception {
        final var card = buildCreditCardLookupResponseWithCounty("de");
        assertThat(this.costs.findByCardIssuingCountry("de")).isEmpty();
        final var form = new CardNumberForm();
        form.setCardNumber("1234567890");

        when(this.creditCardLookupClient
                .getCreditCardDetails(form.toModel().getIssuerIdentificationNumber()))
                .thenReturn(card);

        this.api.perform(post("/api/v1/payment-cards-cost")
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .content(this.json.writeValueAsString(form)))
            .andExpect(status().isNotFound());
    }

    @Test
    public void shouldNotRetrieveClearingCostByCreditCardNumber_whenCreditCardNumberInvalid() throws Exception {
        final var form = new CardNumberForm();
        form.setCardNumber("invalid");

        this.api.perform(post("/api/v1/payment-cards-cost")
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .content(this.json.writeValueAsString(form)))
            .andExpect(status().isUnprocessableEntity());
    }

    private static BintableData buildCreditCardLookupResponseWithCounty(String countryCode) {
        final var country = new BintableDataCountry();
        country.setCode(countryCode);
        final var card = new BintableData();
        card.setCountry(country);
        return card;
    }

}
