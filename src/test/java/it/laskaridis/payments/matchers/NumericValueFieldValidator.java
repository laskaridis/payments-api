package it.laskaridis.payments.matchers;

import it.laskaridis.payments.common.model.EntityModel;
import jakarta.validation.constraints.Min;

import java.util.Optional;

import static it.laskaridis.payments.matchers.IntrospectionUtils.getFieldAnnotation;

public class NumericValueFieldValidator<T extends EntityModel> extends ValidatorAssertions<T> {

    protected NumericValueFieldValidator(final ValidatorAssertions<T> assertion) {
        super(assertion.getActual(), assertion.getClass(), assertion.getFieldName());
    }

    public ValidatorAssertions<T> isInclusivelyEqualTo(final Long value) {
        validateActualUsing(new InclusiveMinValidator<>(this.fieldName, value));
        return this;
    }

    static class InclusiveMinValidator<T extends EntityModel> extends FieldValidator<T> {

        private final Long minValue;

        protected InclusiveMinValidator(final String fieldName, final Long minValue) {
            super(fieldName);
            this.minValue = minValue;
        }

        @Override
        boolean isValid(final T instance) {
            final String fieldName = getFieldName();
            final Optional<Min> minAnnotation = getFieldAnnotation(instance, Min.class, fieldName);
            if (minAnnotation.isPresent() && minValue.equals(minAnnotation.get().value())) {
                return FIELD_VALID;
            } else {
                return FIELD_INVALID;
            }
        }

        @Override
        String getErrorMessage() {
            return String.format("Expected to have @Min(%n) on field %s",
                    this.minValue, getFieldName());
        }
    }
}
