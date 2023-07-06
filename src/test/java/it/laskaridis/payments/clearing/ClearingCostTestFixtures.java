package it.laskaridis.payments.clearing;

import it.laskaridis.payments.clearing.model.ClearingCost;
import it.laskaridis.payments.clearing.model.ClearingCostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ClearingCostTestFixtures {

    @Autowired
    private ClearingCostRepository repository;

    public ClearingCost buildClearingCostWith(String country, String currency, Double amount) {
        ClearingCost clearingCost = new ClearingCost();
        clearingCost.setClearingCostAmount(BigDecimal.valueOf(amount));
        clearingCost.setClearingCostCurrency(currency);
        clearingCost.setCardIssuingCountry(country);
        return clearingCost;
    }

    public ClearingCost buildClearingCostWith(String country) {
        return this.buildClearingCostWith(country, "USD", 100.0);
    }

    public ClearingCost buildClearingCost() {
        return buildClearingCostWith("gr");
    }

    public ClearingCost createClearingCost() {
        ClearingCost clearingCost = buildClearingCost();
        return this.repository.save(clearingCost);
    }

    public ClearingCost createClearingCostWith(String country, String currency, Double amount) {
        ClearingCost clearingCost = buildClearingCostWith(country, currency, amount);
        return this.repository.save(clearingCost);
    }

}
