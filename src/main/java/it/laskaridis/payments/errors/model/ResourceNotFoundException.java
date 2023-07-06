package it.laskaridis.payments.errors.model;

/**
 * Thrown after requests that reference required resources which don't exist.
 */
public class ResourceNotFoundException extends ApplicationException {
    public ResourceNotFoundException(String resource, String description) {
        super(String.format("NotFound.%s", resource), description, NON_RETRYABLE);
    }
}
