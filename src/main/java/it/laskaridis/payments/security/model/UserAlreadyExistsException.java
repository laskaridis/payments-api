package it.laskaridis.payments.security.model;

import it.laskaridis.payments.errors.model.ResourceAlreadyTakenException;

public class UserAlreadyExistsException extends ResourceAlreadyTakenException {
    public UserAlreadyExistsException(String email) {
        super(User.class.getSimpleName(), String.format(
                "User %s already exists.", email));
    }
}
