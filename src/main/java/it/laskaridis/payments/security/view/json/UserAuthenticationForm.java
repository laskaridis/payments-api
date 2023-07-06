package it.laskaridis.payments.security.view.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.laskaridis.payments.common.view.json.JsonViewForm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserAuthenticationForm extends JsonViewForm<UsernamePasswordAuthenticationToken> {

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;

    @Override
    public UsernamePasswordAuthenticationToken toModel() {
        return new UsernamePasswordAuthenticationToken(getEmail(), getPassword());
    }
}
