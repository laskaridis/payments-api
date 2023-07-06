package it.laskaridis.payments.clearing.controller;

import it.laskaridis.payments.clearing.model.ClearingCost;
import it.laskaridis.payments.common.controller.Resource;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.springframework.util.Assert.notNull;

/**
 * Represents the published {@link ClearingCost} resource.
 */
public class ClearingCostResource implements Resource {

    private final ClearingCost model;

    public ClearingCostResource(ClearingCost model) {
        notNull(model, "model can't be null");
        this.model = model;
    }

    @Override
    public URI getLocation() {
        return UriComponentsBuilder.newInstance()
                .pathSegment("countries", "{country}", "clearing_cost")
                .buildAndExpand(this.model.getCardIssuingCountry())
                .toUri();
    }
}
