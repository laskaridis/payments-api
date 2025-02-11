package it.laskaridis.payments.errors.model;

/**
 * Thrown after requests that break the uniqueness of a resource
 */
public class ResourceAlreadyTakenException extends ApplicationException {
    public ResourceAlreadyTakenException(final String resource, final String description) {
        super(String.format("AlreadyTaken.%s", resource), description, NON_RETRYABLE);
    }
}
