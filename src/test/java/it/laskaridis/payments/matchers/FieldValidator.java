package it.laskaridis.payments.matchers;

import it.laskaridis.payments.common.model.EntityModel;
import lombok.RequiredArgsConstructor;

/**
 * Base class extended from all {@link EntityModel} field validators.
 *
 * @param <T> the domain model type
 */
@RequiredArgsConstructor
abstract class FieldValidator<T extends EntityModel> {

    static final boolean FIELD_VALID = true;
    static final boolean FIELD_INVALID = false;

    private final String fieldName;

    public String getFieldName() {
        return fieldName;
    }

    abstract boolean isValid(final T instance);

    abstract String getErrorMessage();
}
