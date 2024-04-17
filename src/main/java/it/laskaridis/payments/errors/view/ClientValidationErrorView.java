package it.laskaridis.payments.errors.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Set;

import jakarta.validation.ConstraintViolation;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

/**
 * Enhances {@link ClientErrorView} with properties specific to resource
 * field validation errors (i.e. {@link ConstraintViolation} errors).
 *
 * By definition all errors of this class are always retriable by clients.
 */
public class ClientValidationErrorView extends ClientErrorView {

    /**
     * Creates a view representation for the specified {@link ConstraintViolation}.
     *
     * @param violation the violation
     * @return a view representing the {@link ConstraintViolation}
     * @throws NullPointerException when specified violation is null
     */
    public static ClientValidationErrorView from(final ConstraintViolation<?> violation) {
        Assert.notNull(violation, "violation can't be null");

        return new ClientValidationErrorView(
                violation.getRootBeanClass().getSimpleName(),
                violation.getPropertyPath().toString(),
                violation.getMessage());
    }

    /**
     * Create a view representation for a set of {@link ConstraintViolation}s.
     *
     * @param violations the set of {@link ConstraintViolation}s
     * @return a {@link List} of view objects representing the specified {@link ConstraintViolation}s
     */
    public static List<ClientValidationErrorView> from(final Set<ConstraintViolation<?>> violations) {
        return violations.stream()
                .map(ClientValidationErrorView::from)
                .collect(toList());
    }

    @JsonProperty("invalid_resource")
    private final String invalidResource;

    @JsonProperty("invalid_field")
    private final String invalidField;

    @JsonProperty("invalid_field_error")
    private final String invalidFieldError;

    public ClientValidationErrorView(final String invalidResource,
                                     final String invalidField,
                                     final String invalidFieldError) {

        super(format("ValidationError.%s", invalidResource),
                format("field `%s` is invalid", invalidField), true);

        Assert.notNull(invalidResource, "invalidResource can't be null");
        Assert.notNull(invalidField, "invalidField can't be null");
        Assert.notNull(invalidFieldError, "invalidFieldError can't be null");

        this.invalidResource = invalidResource;
        this.invalidField = invalidField;
        this.invalidFieldError = invalidFieldError;
    }

    /**
     * @return the invalid resource type
     */
    public String getInvalidResource() {
        return this.invalidResource;
    }

    /**
     * @return the invalid property field name
     */
    public String getInvalidField() {
        return this.invalidField;
    }

    /**
     * @return the invalid property field error description
     */
    public String getInvalidFieldError() {
        return this.invalidFieldError;
    }
}
