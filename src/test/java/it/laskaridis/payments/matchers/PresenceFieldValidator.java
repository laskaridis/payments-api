package it.laskaridis.payments.matchers;

import it.laskaridis.payments.common.model.EntityModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

class PresenceFieldValidator<T extends EntityModel> extends FieldValidator<T> {

    protected PresenceFieldValidator(final String fieldName) {
        super(fieldName);
    }

    @Override
    boolean isValid(final T instance) {
        final var fieldName = getFieldName();
        final var notNullAnnotation = IntrospectionUtils.getFieldAnnotation(instance, NotNull.class, fieldName);
        final var notEmptyAnnotation = IntrospectionUtils.getFieldAnnotation(instance, NotEmpty.class, fieldName);
        final var notBlankAnnotation = IntrospectionUtils.getFieldAnnotation(instance, NotBlank.class, fieldName);
        return notNullAnnotation.isPresent()
                || notEmptyAnnotation.isPresent()
                || notBlankAnnotation.isPresent();
    }

    @Override
    String getErrorMessage() {
        return String.format("Expected to have @NotNull, @NotEmpty or @NotBlank on field %s", getFieldName());
    }

}
