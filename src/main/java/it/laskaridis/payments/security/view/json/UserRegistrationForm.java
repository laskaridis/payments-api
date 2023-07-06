package it.laskaridis.payments.security.view.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.laskaridis.payments.common.view.json.JsonViewForm;
import it.laskaridis.payments.security.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRegistrationForm extends JsonViewForm<User> {

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String plaintextPassword;

    public UserRegistrationForm(
            String fullName,
            String email,
            String plaintextPassword) {

        this.fullName = fullName;
        this.email = email;
        this.plaintextPassword = plaintextPassword;
    }

    @Override
    public User toModel() {
        User user = new User();
        user.setEmail(getEmail());
        user.setFullName(getFullName());
        user.setPassword(getPlaintextPassword());
        return user;
    }
}
