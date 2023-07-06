package it.laskaridis.payments.utils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Marker annotation that identifies a class as immutable and
 * hence inherently thread safe.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Immutable {
}
