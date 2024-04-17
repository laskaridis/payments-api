package it.laskaridis.payments.matchers;

import it.laskaridis.payments.common.model.EntityModel;
import jakarta.validation.constraints.Max;
import org.hibernate.validator.constraints.Length;

import java.util.Optional;

import static it.laskaridis.payments.matchers.IntrospectionUtils.getFieldAnnotation;

class MaxLengthFieldValidator<T extends EntityModel> extends FieldValidator<T> {

    private final int maxValue;

    protected MaxLengthFieldValidator(final String fieldName, final int maxValue) {
        super(fieldName);
        this.maxValue = maxValue;
    }

    @Override
    boolean isValid(final T instance) {
        final var fieldName = getFieldName();
        final var maxAnnotation = getFieldAnnotation(instance, Max.class, fieldName);
        if (maxAnnotation.isPresent() && maxAnnotation.get().value() == this.maxValue)
            return FIELD_VALID;
        final var lengthAnnotation = getFieldAnnotation(instance, Length.class, fieldName);
        if (lengthAnnotation.isPresent() && lengthAnnotation.get().max() == this.maxValue)
            return FIELD_VALID;
        return FIELD_INVALID;
    }

    @Override
    String getErrorMessage() {
        return String.format("Expected to have @Length(max = %s) or @Min(value = %s) on field %s",
                this.maxValue, this.maxValue, getFieldName());
    }
}
