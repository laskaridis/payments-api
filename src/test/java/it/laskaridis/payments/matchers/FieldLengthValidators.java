package it.laskaridis.payments.matchers;

import it.laskaridis.payments.common.model.EntityModel;

public class FieldLengthValidators<T extends EntityModel> extends ValidatorAssertions<T> {

    protected FieldLengthValidators(T t, Class<?> selfType, String fieldName) {
        super(t, selfType, fieldName);
    }

    public ValidatorAssertions<T> isAtMost(int max) {
        validateActualUsing(new MaxLengthFieldValidator<>(this.fieldName, max));
        return this;
    }

    public ValidatorAssertions<T> isExactly(int size) {
        validateActualUsing(new ExactLengthFieldValidator<>(this.fieldName, size));
        return this;
    }
}
