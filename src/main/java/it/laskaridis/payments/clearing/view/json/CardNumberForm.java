package it.laskaridis.payments.clearing.view.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.laskaridis.payments.clearing.model.PrimaryAccountNumber;
import it.laskaridis.payments.common.view.json.JsonViewForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public final class CardNumberForm extends JsonViewForm<PrimaryAccountNumber> {

    @JsonProperty("card_number")
    private String cardNumber;

    @Override
    public PrimaryAccountNumber toModel() {
        return new PrimaryAccountNumber(getCardNumber());
    }
}
