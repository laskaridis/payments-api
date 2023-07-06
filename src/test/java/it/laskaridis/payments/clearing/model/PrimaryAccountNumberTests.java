package it.laskaridis.payments.clearing.model;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class PrimaryAccountNumberTests {

    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void shouldConvertToString() {
        var subject = new PrimaryAccountNumber("1234567890");
        assertThat(subject.toString()).isEqualTo("1234567890");
    }

    @Test
    public void shouldCreateOnlyValidPrimaryAccountNumbers() {

        shouldNotBeLessThanEightDigitsLong();
        shouldNotBeMoreThanNineteenDigitsLong();
        shouldNotContainAlphanumericCharacters();

        mayBeEightDigitsLong();
        mayBeNineteenDigitsLong();
        mayBeBetweenEightAndNineteenDigitsLong();
    }

    private void mayBeBetweenEightAndNineteenDigitsLong() {
        var aSixteenDigitString = "1234567890123456";
        var subject = new PrimaryAccountNumber(aSixteenDigitString);

        assertValid(subject);
        assertThat(subject.getIssuerIdentificationNumber()).isEqualTo("123456");
        assertThat(subject.getAccountIdentificationNumber()).isEqualTo("7890123456");
    }

    private void mayBeNineteenDigitsLong() {
        var aNinteeenDigitString = "1234567890123456789";
        var subject = new PrimaryAccountNumber(aNinteeenDigitString);

        assertValid(subject);
        assertThat(subject.getIssuerIdentificationNumber()).isEqualTo("123456");
        assertThat(subject.getAccountIdentificationNumber()).isEqualTo("7890123456789");
    }

    private void mayBeEightDigitsLong() {
        var anEightDigitString = "12345678";
        var subject = new PrimaryAccountNumber(anEightDigitString);

        assertValid(subject);
        assertThat(subject.getIssuerIdentificationNumber()).isEqualTo("123456");
        assertThat(subject.getAccountIdentificationNumber()).isEqualTo("78");
    }

    private void shouldNotContainAlphanumericCharacters() {
        var anEightCharsAlphanumericString = "1234567a";
        var subject = new PrimaryAccountNumber(anEightCharsAlphanumericString);
        assertNotValid(subject);
    }

    private void shouldNotBeMoreThanNineteenDigitsLong() {
        var aTwentyDigitString = "12345678901234567890";
        var subject = new PrimaryAccountNumber(aTwentyDigitString);
        assertNotValid(subject);
    }

    private void shouldNotBeLessThanEightDigitsLong() {
        var aSevenDigitString = "1234567";
        var subject = new PrimaryAccountNumber(aSevenDigitString);
        assertNotValid(subject);
    }

    private void assertNotValid(PrimaryAccountNumber subject) {
        var errors = this.validator.validate(subject);
        assertThat(errors).isNotEmpty();
    }

    private void assertValid(PrimaryAccountNumber subject) {
        var errors = this.validator.validate(subject);
        assertThat(errors).isEmpty();
    }

}
