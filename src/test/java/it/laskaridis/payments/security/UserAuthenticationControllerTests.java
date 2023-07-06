package it.laskaridis.payments.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.laskaridis.payments.IntegrationTest;
import it.laskaridis.payments.security.model.User;
import it.laskaridis.payments.security.model.UserService;
import it.laskaridis.payments.security.view.json.UserAuthenticationForm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
public class UserAuthenticationControllerTests {

    @Autowired
    private MockMvc api;

    @Autowired
    private ObjectMapper json;

    @Autowired
    private UserService userService;

    @Test
    public void shouldAuthenticateUser_whenCredentialsMatch() throws Exception {
        var user = new User();
        user.setFullName("Jane Doe");
        user.setEmail("jane@localhost");
        user.setPassword("pass");
        this.userService.register(user);

        var form = new UserAuthenticationForm("jane@localhost", "pass");

        this.api.perform(post("/api/v1/logins")
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .content(this.json.writeValueAsString(form)))
            .andExpect(status().isOk())
            .andExpect(header().exists(HttpHeaders.AUTHORIZATION));
    }

    @Test
    public void shouldNotAuthenticateUser_whenCredentialsDontMatch() throws Exception {
        var user = new User();
        user.setFullName("Jane Doe");
        user.setEmail("jane@localhost");
        user.setPassword("pass");
        this.userService.register(user);

        var form = new UserAuthenticationForm(user.getEmail(), user.getPassword());

        this.api.perform(post("/api/v1/logins")
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .content(this.json.writeValueAsString(form)))
            .andExpect(status().isUnauthorized());
    }
}
