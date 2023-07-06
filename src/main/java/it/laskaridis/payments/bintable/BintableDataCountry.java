package it.laskaridis.payments.bintable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true) // for forward compatibility
public class BintableDataCountry {

    @JsonProperty("name")
    private String name;

    @JsonProperty("code")
    private String code;

    @JsonProperty("flag")
    private String flag;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("currency_code")
    private String currencyCode;
}
