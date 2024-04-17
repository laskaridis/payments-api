package it.laskaridis.payments.matchers;

import it.laskaridis.payments.common.model.EntityModel;
import org.assertj.core.api.AbstractAssert;

/**
 * Provides unit test matchers for <em>javax.validation.constraints</em> annotations.
 * Each matcher's implementation is based on the presence of a specific annotation on
 * some field on the class instance under test. Although the testing strategy does
 * couple the test code to the internal implementation of domain entities, therefore
 * making it more fragile in the face of change, it is fast and practical which is
 * good enough in any occasion where the validation model is unlikely to change.
 *
 * @param <T> the class of the {@link EntityModel} instance under test
 */
public class ValidatorAssertions<T extends EntityModel> extends AbstractAssert<ValidatorAssertions<T>, T> {

    public static <T extends EntityModel> ValidatorAssertions<T> assertThat(final T model) {
        return new ValidatorAssertions(model, ValidatorAssertions.class);
    }

    protected ValidatorAssertions(final T t, final Class<?> selfType) {
        super(t, selfType);
    }

    protected ValidatorAssertions(final T t, final Class<?> selfType, final String fieldName) {
        super(t, selfType);
        this.fieldName = fieldName;
    }

    // TODO: make private
    protected String fieldName;

    protected T getActual() {
        return this.actual;
    }

    protected String getFieldName() {
        return this.fieldName;
    }

    public ValidatorAssertions<T> shouldValidatePresenceOf(final String fieldName) {
        validateActualUsing(new PresenceFieldValidator<>(fieldName));
        return this;
    }

    public NumericValueFieldValidator<T> shouldValidateMinimumValueOf(final String fieldName) {
        this.fieldName = fieldName;
        return new NumericValueFieldValidator<>(this);
    }

    public FieldLengthValidators<T> shouldValidateLengthOf(final String fieldName) {
        this.fieldName = fieldName;
        return new FieldLengthValidators<>(this.actual, this.myself.getClass(), this.fieldName);
    }

    protected void validateActualUsing(final FieldValidator<T> validator) {
        if (!validator.isValid(this.actual))
            failWithMessage(validator.getErrorMessage());
    }

}
