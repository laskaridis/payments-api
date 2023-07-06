package it.laskaridis.payments.utils;

/**
 * Contains static utility methods.
 */
public final class Lang {

    private Lang() { }

    /**
     * Negates the specified boolean value. May be used instead
     * not operator to improve readability in negated conditionals
     * (i.e. for syntactic sugar):
     *
     * <pre>
     * if (not(isSomeBooleanResult())) { }
     * // vs:
     * if (!isSomeBooleanResult()) { }
     * </pre>
     *
     * @param value the boolean value to negate
     * @return the negated boolean value
     */
    public static boolean not(final boolean value) {
        return !value;
    }

}
