package it.laskaridis.payments;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.annotation.*;

/**
 * Annotation used to mark test files end-to-end tests which in order to
 * run require the entire spring container environment to start up. Use
 * this only if you really must write tests that go through all layers
 * of the stack.
 *
 * Each test method will run inside a database transaction which will be
 * rolled back after its execution exits (either returns or throws an
 * exception). Hence, it can be assumed that there are no side-effects
 * of any method that will be visible by others.
 */
@Tag("IntegrationTest")
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
@Documented
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public @interface IntegrationTest {
}
