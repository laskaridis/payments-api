package it.laskaridis.payments.errors.model;

/**
 * Thrown after requests that reference required resources which don't exist.
 */
public class ResourceNotFoundException extends ApplicationException {
    public ResourceNotFoundException(final String resource, final String description) {
        super(String.format("NotFound.%s", resource), description, NON_RETRYABLE);
    }
}
