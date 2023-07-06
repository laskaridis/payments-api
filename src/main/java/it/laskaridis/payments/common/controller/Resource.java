package it.laskaridis.payments.common.controller;

import java.net.URI;

public interface Resource {

    /**
     * Returns the location of the modelled resource
     *
     * @return the resource's location
     */
    URI getLocation();
}
