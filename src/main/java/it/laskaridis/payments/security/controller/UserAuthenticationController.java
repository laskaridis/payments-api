package it.laskaridis.payments.security.controller;

import it.laskaridis.payments.security.model.UserService;
import it.laskaridis.payments.security.view.json.UserAuthenticationForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserAuthenticationController {

    private final UserService userService;

    @PostMapping("/logins")
    public ResponseEntity<Void> login(@RequestBody final UserAuthenticationForm credentials) {
        final var jwt = this.userService.authenticate(credentials.toModel());
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", jwt))
                .build();
    }

}
