package it.laskaridis.payments.bintable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true) // for forward compatibility
public class BintableDataCard {

    @JsonProperty("scheme")
    private String scheme;

    @JsonProperty("type")
    private String type;

    @JsonProperty("category")
    private String category;

    @JsonProperty("length")
    private int length;

    @JsonProperty("checkluhn")
    private int checkLuhn;

    @JsonProperty("cvvlen")
    private int cvvLen;
}
