package it.laskaridis.payments.bintable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true) // for forward compatibility
public class BintableData {

    @JsonProperty("card")
    private BintableDataCard card;

    @JsonProperty("country")
    private BintableDataCountry country;

    @JsonProperty("bank")
    private BintableDataBank bank;
}
