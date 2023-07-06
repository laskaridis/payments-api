package it.laskaridis.payments.clearing.view.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.laskaridis.payments.clearing.model.CardIssuer;
import it.laskaridis.payments.common.view.json.EntityModelJsonViewAdapter;

public class CardIssuerView extends EntityModelJsonViewAdapter<CardIssuer> {

    public CardIssuerView(CardIssuer model) {
        super(model);
    }

    @JsonProperty("issuer_identification_number")
    public String getIssuerIdentificationNumber() {
        return getModel().getIssuerIdentificationNumber();
    }

    @JsonProperty("bank_country_code")
    public String getBankCountryCode() {
        return getModel().getBankCountryCode();
    }

    @JsonProperty("clearing_cost")
    public ClearingCostView getClearingCost() {
        return new ClearingCostView(getModel().getClearingCost());
    }
}
