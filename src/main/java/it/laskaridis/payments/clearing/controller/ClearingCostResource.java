package it.laskaridis.payments.clearing.controller;

import it.laskaridis.payments.clearing.model.ClearingCost;
import it.laskaridis.payments.common.controller.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * Represents the published {@link ClearingCost} resource.
 */
@RequiredArgsConstructor
public class ClearingCostResource implements Resource {

    private final ClearingCost model;

    @Override
    public URI getLocation() {
        return UriComponentsBuilder.newInstance()
                .pathSegment("countries", "{country}", "clearing_cost")
                .buildAndExpand(this.model.getCardIssuingCountry())
                .toUri();
    }
}
