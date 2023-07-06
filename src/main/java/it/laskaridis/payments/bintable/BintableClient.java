package it.laskaridis.payments.bintable;

import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

import static io.netty.channel.ChannelOption.CONNECT_TIMEOUT_MILLIS;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Client for the bintable remote service through which information
 * about card issuers is retrieved.
 */
@Component
@Slf4j
public class BintableClient {

    private final BintableClientConfig config;

    private final WebClient client;

    public BintableClient(final BintableClientConfig config) {
        this.config = config;
        this.client = createWebClient();
    }

    private WebClient createWebClient() {
        // configures connection timeouts:
        HttpClient http = HttpClient.create();
        http.option(CONNECT_TIMEOUT_MILLIS, this.config.getConnectTimeoutMillis())
            .responseTimeout(Duration.ofMillis(this.config.getConnectTimeoutMillis()))
            .doOnConnected(connection -> connection
                .addHandlerLast(new ReadTimeoutHandler(this.config.getReadTimeoutMillis(), MILLISECONDS))
                .addHandlerLast(new WriteTimeoutHandler(this.config.getWriteTimeoutMillis(), MILLISECONDS))
            );

        return WebClient.builder()
            .baseUrl(this.config.getBaseUrl())
            .defaultHeader(CONTENT_TYPE.toString(), APPLICATION_JSON_VALUE)
            .defaultHeader(ACCEPT.toString(), APPLICATION_JSON_VALUE)
            .defaultHeader(ACCEPT_CHARSET.toString(), UTF_8.toString())
            .clientConnector(new ReactorClientHttpConnector(http))
            .build();
    }

    public BintableData getCreditCardDetails(String iin) throws BintableClientException {
        log.debug("initiating lookup for IIN: {}", iin);
        var url = this.config.getBaseUrl() + "/{number}?api_key={apiKey}";
        var mono = this.client.get()
            .uri(url, iin, this.config.getApiKey())
            .accept(APPLICATION_JSON)
            .acceptCharset(UTF_8)
            .retrieve()
            .onStatus(HttpStatusCode::isError, response -> {
                log.error("failed to retrieve credit card details for IIN {}, response code {}", iin, response.statusCode());
                return Mono.error(new BintableClientException("failed to retrieve card details"));
            })
            .bodyToMono(BintableResponse.class);

        var response = mono.block();
        if (response.getResult() == 200) {
            return response.getData();
        } else {
            log.error("failed to retrieve credit card details for IIN {}, result code {}", iin, response.getResult());
            throw new BintableClientException(response.getMessage());
        }
    }
}
