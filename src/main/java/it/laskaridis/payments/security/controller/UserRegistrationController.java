package it.laskaridis.payments.security.controller;

import it.laskaridis.payments.security.model.UserService;
import it.laskaridis.payments.security.view.json.UserRegistrationForm;
import it.laskaridis.payments.security.view.json.UserView;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class UserRegistrationController {

    private final UserService userService;
    public UserRegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public UserView register(@RequestBody UserRegistrationForm form) {
        var model = this.userService.register(form.toModel());
        return new UserView(model);
    }
}
