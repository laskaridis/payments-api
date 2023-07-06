package it.laskaridis.payments.security.model;

import it.laskaridis.payments.errors.model.ResourceNotFoundException;
import it.laskaridis.payments.security.model.User;

public class UserNotFoundException extends ResourceNotFoundException {
    public UserNotFoundException(String email) {
        super(User.class.getSimpleName(), String.format(
                "User %s does not exist.", email));
    }
}
