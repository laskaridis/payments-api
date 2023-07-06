package it.laskaridis.payments.bintable;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class BintableClientResponseTests {

    @Test
    public void shouldParseCorrectly() throws IOException {
        String json = """
        {
           "result": 200,
           "message": "SUCCESS",
           "data": {
             "card": {
               "scheme": "Visa",
               "type": "Debit",
               "category": "Corporate t&e",
               "length": -1,
               "checkluhn": 1,
               "cvvlen": 3
             },
             "country": {
               "name": "Egypt",
               "code": "eg",
               "flag": "🇪🇬",
               "currency": "Egyptian pound",
               "currency_code": "EGP"
             },
             "bank": {
               "name": "ARAB INTERNATIONAL BANK",
               "website": "http://www.aib.com.eg:81/web/wps/portal/enaib",
               "phone": "23918794"
             }
           }
        }
        """;

        ObjectMapper parser = new ObjectMapper();

        BintableResponse result = parser.reader().readValue(json, BintableResponse.class);
        assertThat(result.getResult()).isEqualTo(200);
        assertThat(result.getMessage()).isEqualTo("SUCCESS");

        BintableDataCard card = result.getData().getCard();
        assertThat(card.getScheme()).isEqualTo("Visa");
        assertThat(card.getType()).isEqualTo("Debit");
        assertThat(card.getCategory()).isEqualTo("Corporate t&e");
        assertThat(card.getLength()).isEqualTo(-1);
        assertThat(card.getCheckLuhn()).isEqualTo(1);
        assertThat(card.getCvvLen()).isEqualTo(3);

        BintableDataCountry country = result.getData().getCountry();
        assertThat(country.getName()).isEqualTo("Egypt");
        assertThat(country.getCode()).isEqualTo("eg");
        assertThat(country.getCurrency()).isEqualTo("Egyptian pound");
        assertThat(country.getCurrencyCode()).isEqualTo("EGP");

        BintableDataBank bank = result.getData().getBank();
        assertThat(bank.getName()).isEqualTo("ARAB INTERNATIONAL BANK");
        assertThat(bank.getWebsite()).isEqualTo("http://www.aib.com.eg:81/web/wps/portal/enaib");
        assertThat(bank.getPhone()).isEqualTo("23918794");
    }
}
