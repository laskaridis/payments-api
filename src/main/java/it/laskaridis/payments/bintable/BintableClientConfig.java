package it.laskaridis.payments.bintable;

import it.laskaridis.payments.utils.Immutable;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration for bintable client
 */
@Immutable
@ConfigurationProperties(prefix = "bintable")
public final class BintableClientConfig {

    private final String apiKey;
    private final String baseUrl;
    private final int connectTimeoutMillis;
    private final int readTimeoutMillis;
    private final int writeTimeoutMillis;

    public BintableClientConfig(
            String apiKey,
            String baseUrl,
            int connectTimeoutMillis,
            int readTimeoutMillis,
            int writeTimeoutMillis) {

        this.apiKey = apiKey;
        this.baseUrl = baseUrl;
        this.connectTimeoutMillis = connectTimeoutMillis;
        this.readTimeoutMillis = readTimeoutMillis;
        this.writeTimeoutMillis = writeTimeoutMillis;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public int getConnectTimeoutMillis() {
        return connectTimeoutMillis;
    }

    public int getReadTimeoutMillis() {
        return readTimeoutMillis;
    }

    public int getWriteTimeoutMillis() {
        return writeTimeoutMillis;
    }

}
