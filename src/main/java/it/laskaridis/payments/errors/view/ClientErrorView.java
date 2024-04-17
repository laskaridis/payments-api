package it.laskaridis.payments.errors.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.laskaridis.payments.errors.model.ApplicationException;
import org.springframework.util.Assert;

/**
 * Renders a client view error
 */
public class ClientErrorView {

    /**
     * Factory method to create a {@link ClientErrorView} (view) instance from a corresponding
     * {@link ApplicationException} model.
     */
    public static ClientErrorView from(final ApplicationException exception) {
        return new ClientErrorView(
                exception.getCode(),
                exception.getDescription(),
                exception.isRetryable());
    }

    @JsonProperty("error_code")
    private final String errorCode;

    @JsonProperty("error_description")
    private final String errorDescription;

    @JsonProperty("request_retryable")
    private final boolean requestRetryable;

    public ClientErrorView(final String errorCode, final String errorDescription, final boolean requestRetryable) {
        Assert.notNull(errorCode, "errorCode can't be null");
        Assert.notNull(errorDescription, "errorDescription can't be null");

        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
        this.requestRetryable = requestRetryable;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public String getErrorDescription() {
        return this.errorDescription;
    }

    /**
     * @see ApplicationException#isRetryable()
     */
    public boolean isRequestRetryable() {
        return this.requestRetryable;
    }

}
