package it.laskaridis.payments.utils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Marker annotation that identifies a domain model class
 * as a value object.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ValueObject {
}
