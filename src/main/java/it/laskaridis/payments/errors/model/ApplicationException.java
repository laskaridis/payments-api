package it.laskaridis.payments.errors.model;

public class ApplicationException extends RuntimeException {

    /**
     * Error may be caused from a temporal condition so it would make sense
     * for callers to re-try the operation (behaving nicely and using some
     * sort of back-off strategy).
     */
    public static final boolean RETRYABLE = true;

    /**
     * Doesn't make sense for the caller should not re-try the operator (i.e.
     * they would get back the same error).
     */
    public static final boolean NON_RETRYABLE = false;

    private final String code;
    private final String description;

    private final boolean retryable;

    public ApplicationException(final String code, final String description, boolean retryable) {
        this.code = code;
        this.description = description;
        this.retryable = retryable;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Some errors are transient and would make sense for the client to retry (using
     * some sort of a back-off strategy). Others are not and clients have nothing to
     * gain by re-trying the operation. This intent behind this parameter is to provide
     * a hint to clients about weather it make sense to retry a failed operation or not.
     *
     * @return <code>true</code> if it makes sense to re-try the failed operation, <code>false</code>otherwise
     */
    public boolean isRetryable() {
        return retryable;
    }
}
