package it.laskaridis.payments.clearing.view.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.laskaridis.payments.clearing.model.ClearingCost;
import it.laskaridis.payments.common.model.Money;
import it.laskaridis.payments.common.view.json.JsonViewForm;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ClearingCostForm extends JsonViewForm<ClearingCost> {

    public ClearingCostForm(Money money) {
        this.clearingCostAmount = money.amount();
        this.clearingCostCurrency = money.currency();
    }

    @JsonProperty("clearing_cost_currency")
    private String clearingCostCurrency;

    @JsonProperty("clearing_cost_amount")
    private BigDecimal clearingCostAmount;

    @Override
    public ClearingCost toModel() {
        ClearingCost cost = new ClearingCost();
        cost.setClearingCostAmount(getClearingCostAmount());
        cost.setClearingCostCurrency(getClearingCostCurrency());
        return cost;
    }
}

