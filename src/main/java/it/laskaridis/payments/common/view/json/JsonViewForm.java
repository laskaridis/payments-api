package it.laskaridis.payments.common.view.json;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true) // for forward compatibility
public abstract class JsonViewForm<T> {

    /**
     * Generates a model instance, populated with the form's
     * submitted values.
     *
     * @return a model instance
     */
    @JsonIgnore
    public abstract T toModel();
}
