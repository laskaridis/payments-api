package it.laskaridis.payments.clearing.model;

import it.laskaridis.payments.common.model.EntityModel;
import it.laskaridis.payments.common.model.Money;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.util.Set;

@Data
@NoArgsConstructor // required for JPA
@Entity
@Table(name = "clearing_costs")
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper = true)
public class ClearingCost extends EntityModel {

    @Column(name = "card_issuing_country")
    @NotNull @Length(min = 2, max = 2)
    private String cardIssuingCountry;

    @Column(name = "clearing_cost_amount")
    @NotNull @Min(0)
    private BigDecimal clearingCostAmount;

    @Column(name = "clearing_cost_currency")
    @NotNull @Length(min = 3, max = 3)
    private String clearingCostCurrency;

    @OneToMany(mappedBy = "clearingCost", cascade = CascadeType.REMOVE)
    private Set<CardIssuer> cardIssuers;

    public Money getClearingCost() {
        return new Money(this.clearingCostAmount, this.clearingCostCurrency);
    }

    public void setClearingCost(Money cost) {
        this.clearingCostAmount = cost.amount();
        this.clearingCostCurrency = cost.currency();
    }
}
