package it.laskaridis.payments.matchers;

import it.laskaridis.payments.common.model.EntityModel;

public class FieldLengthValidators<T extends EntityModel> extends ValidatorAssertions<T> {

    protected FieldLengthValidators(final T t, final Class<?> selfType, final String fieldName) {
        super(t, selfType, fieldName);
    }

    public ValidatorAssertions<T> isAtMost(final int max) {
        validateActualUsing(new MaxLengthFieldValidator<>(this.fieldName, max));
        return this;
    }

    public ValidatorAssertions<T> isExactly(final int size) {
        validateActualUsing(new ExactLengthFieldValidator<>(this.fieldName, size));
        return this;
    }
}
