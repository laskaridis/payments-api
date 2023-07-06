package it.laskaridis.payments.clearing.view.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.laskaridis.payments.clearing.model.ClearingCost;
import it.laskaridis.payments.common.view.json.EntityModelJsonViewAdapter;
import it.laskaridis.payments.utils.Immutable;

import java.math.BigDecimal;

/**
 * Adapts a {@link ClearingCost} model to a JSON view.
 */
@Immutable
public final class ClearingCostView extends EntityModelJsonViewAdapter<ClearingCost> {

    public ClearingCostView(final ClearingCost model) {
        super(model);
    }

    public static ClearingCostView fromModel(final ClearingCost model) {
        return new ClearingCostView(model);
    }

    @JsonProperty("card_issuing_country")
    public String getCardIssuingCountry() {
        return getModel().getCardIssuingCountry();
    }

    @JsonProperty("clearing_cost_amount")
    public BigDecimal getClearingCostAmount() {
        return getModel().getClearingCostAmount();
    }

    @JsonProperty("clearing_cost_currency")
    public String getClearingCostCurrency() {
        return getModel().getClearingCostCurrency();
    }
}
