package it.laskaridis.payments.matchers;

import org.springframework.util.Assert;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Optional;

final class IntrospectionUtils {

    private IntrospectionUtils() {}

    static <T extends Annotation> Optional<T> getFieldAnnotation(
            final Object instance,
            final Class<T> annotationClass,
            final String fieldName) {

        Assert.notNull(instance, "instance can't be null");
        Assert.notNull(annotationClass, "annotationClass can't be null");
        Assert.notNull(fieldName, "fieldName can't be null");

        Field field;
        try {
            field = instance.getClass().getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        T annotation= field.getAnnotation(annotationClass);
        return Optional.ofNullable(annotation);
    }

}
