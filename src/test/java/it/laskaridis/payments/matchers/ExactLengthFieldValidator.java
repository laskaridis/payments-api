package it.laskaridis.payments.matchers;

import it.laskaridis.payments.common.model.EntityModel;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.hibernate.validator.constraints.Length;

import java.util.Optional;

class ExactLengthFieldValidator<T extends EntityModel> extends FieldValidator<T> {

    private final int exactLength;

    protected ExactLengthFieldValidator(final String fieldName, int exactLength) {
        super(fieldName);
        this.exactLength = exactLength;
    }

    @Override
    boolean isValid(final T instance) {
        final String fieldName = getFieldName();

        final Optional<Length> lengthAnnotation = IntrospectionUtils.getFieldAnnotation(instance, Length.class, fieldName);
        if (lengthAnnotation.isPresent()
                && lengthAnnotation.get().min() == this.exactLength
                && lengthAnnotation.get().max() == this.exactLength) {
            return FIELD_VALID;
        }

        final Optional<Max> maxAnnotation = IntrospectionUtils.getFieldAnnotation(instance, Max.class, fieldName);
        final Optional<Min> minAnnotation = IntrospectionUtils.getFieldAnnotation(instance, Min.class, fieldName);
        if (maxAnnotation.isPresent() && minAnnotation.isPresent()
                && maxAnnotation.get().value() == this.exactLength
                && minAnnotation.get().value() == this.exactLength) {
            return FIELD_VALID;
        }

        return FIELD_INVALID;
    }

    @Override
    String getErrorMessage() {
        return String.format(
                "Expected to either have @Length(min = %s, max = %s) " +
                        "or both @Max(value = %s) and @Min(value = %s) on field %s",
                this.exactLength, this.exactLength,
                this.exactLength, this.exactLength, getFieldName()
        );
    }
}
