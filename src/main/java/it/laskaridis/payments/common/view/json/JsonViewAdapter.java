package it.laskaridis.payments.common.view.json;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.laskaridis.payments.common.view.ViewAdapter;
import org.springframework.util.Assert;

/**
 * Used primarily as a "marker interface" to identify classes that
 * adapt a domain model into a JSON view.
 *
 * @param <T> the type of the model class being adapted.
 */
public abstract class JsonViewAdapter<T> implements ViewAdapter<T> {

    @JsonIgnore // should not serialize the model directly
    private final T model;

    public JsonViewAdapter(final T model) {
        Assert.notNull(model, "model can't be null");
        this.model = model;
    }

    @Override
    public T getModel() {
        return this.model;
    }
}
