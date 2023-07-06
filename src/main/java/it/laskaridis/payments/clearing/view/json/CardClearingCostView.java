package it.laskaridis.payments.clearing.view.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.laskaridis.payments.clearing.model.ClearingCost;
import it.laskaridis.payments.common.view.json.JsonViewAdapter;
import it.laskaridis.payments.utils.Immutable;

import java.math.BigDecimal;

@Immutable
public final class CardClearingCostView extends JsonViewAdapter<ClearingCost> {

    public CardClearingCostView(final ClearingCost model) {
        super(model);
    }

    @JsonProperty("country")
    public String getCountry() {
        return getModel().getCardIssuingCountry();
    }

    @JsonProperty("cost")
    public BigDecimal getCost() {
        return getModel().getClearingCostAmount();
    }
}
