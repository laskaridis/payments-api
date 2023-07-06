package it.laskaridis.payments.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.laskaridis.payments.IntegrationTest;
import it.laskaridis.payments.security.model.UserRepository;
import it.laskaridis.payments.security.view.json.UserRegistrationForm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
public class UserRegistrationControllerTests {

    @Autowired
    private MockMvc api;

    @Autowired
    private ObjectMapper json;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldRegisterUser() throws Exception {
        var form = new UserRegistrationForm(
                "John Doe",
                "john@localhost",
                "johnspass");

        var response = this.api.perform(post("/api/v1/users")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(this.json.writeValueAsString(form)));

        var user = this.userRepository.findByEmail(form.getEmail()).get();
        assertThat(user.getEmail()).isEqualTo(form.getEmail());
        assertThat(user.getFullName()).isEqualTo(form.getFullName());
        assertThat(user.getPassword()).isNotEqualTo(form.getPlaintextPassword());

        response
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(user.getId().toString()))
            .andExpect(jsonPath("$.version").exists())
            .andExpect(jsonPath("$.created_at").exists())
            .andExpect(jsonPath("$.last_modified_at").exists())
            .andExpect(jsonPath("$.full_name").value(user.getFullName()))
            .andExpect(jsonPath("$.email").value(user.getEmail()))
            .andExpect(jsonPath("$.password").doesNotExist());
    }

    @Test
    public void shouldNotRegisterUser_whenAlreadyExists() throws Exception {
        var form = new UserRegistrationForm();
        form.setFullName("Jane Doe");
        form.setEmail("jane@localhost");
        form.setPlaintextPassword("pass");
        this.userRepository.save(form.toModel());

        this.api.perform(post("/api/v1/users")
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .content(this.json.writeValueAsString(form)))
            .andExpect(status().isUnprocessableEntity());
    }
}
