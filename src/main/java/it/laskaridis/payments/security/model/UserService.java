package it.laskaridis.payments.security.model;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtEncoder jwtEncoder;

    private final AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtEncoder jwtEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Creates the specified user, if one does not exist already.
     *
     * @param user the user to create
     * @return the created user
     * @throws UserAlreadyExistsException if user email is already taken
     */
    public User register(User user) {
        Optional<User> existingUser = this.userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException(user.getEmail());
        }

        encodeUserPassword(user);
        return this.userRepository.save(user);
    }

    private void encodeUserPassword(User user) {
        String plaintextPassword = user.getPassword();
        String encodedPassword = this.passwordEncoder.encode(plaintextPassword);
        user.setPassword(encodedPassword);
    }

    /**
     * Authenticates a user with the provided credentials.
     *
     * @param credentials the credentials to authenticate.
     * @return a JWT access token for that user
     * @throws org.springframework.security.authentication.BadCredentialsException when provided credentials are invalid
     */
    public String authenticate(Authentication credentials) {
        var authentication = this.authenticationManager.authenticate(credentials);
        var principal = (User) authentication.getPrincipal();
        return generateJwt(principal);
    }

    private String generateJwt(User principal) {
        var now = Instant.now();
        var claims = JwtClaimsSet.builder()
                .issuer("laskaridis.it")
                .issuedAt(now)
                // TODO: externalize this in configuration
                .expiresAt(now.plusSeconds(60000))
                .subject(principal.getEmail())
                .build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
