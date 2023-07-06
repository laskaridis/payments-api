package it.laskaridis.payments.bintable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true) // for forward compatibility
public class BintableResponse {

    @JsonProperty("result")
    private int result;

    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    private BintableData data;

}
