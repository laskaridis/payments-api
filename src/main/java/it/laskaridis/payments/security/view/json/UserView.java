package it.laskaridis.payments.security.view.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.laskaridis.payments.common.view.json.EntityModelJsonViewAdapter;
import it.laskaridis.payments.security.model.User;

public class UserView extends EntityModelJsonViewAdapter<User> {

    public UserView(User model) {
        super(model);
    }

    @JsonProperty("full_name")
    public String getFullName() {
        return getModel().getFullName();
    }

    @JsonProperty("email")
    public String getEmail() {
        return getModel().getEmail();
    }
}
