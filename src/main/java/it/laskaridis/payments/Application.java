package it.laskaridis.payments;

import it.laskaridis.payments.bintable.BintableClientConfig;
import it.laskaridis.payments.common.model.EntityModel;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

@SpringBootApplication
@EnableJpaAuditing
@EnableTransactionManagement
@EnableCaching
@EnableConfigurationProperties(BintableClientConfig.class)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);
		return transactionManager;
	}

	/**
	 * Provides support for <code>Etag</code> based queries from callers (i.e. "If-None-Match" and
	 * "If-Match") to enable caching between the API and the caller (or any downstream intermediaries).
	 *
	 * This is a shallow implementation, meaning that the request will still be processed in order to
	 * calculate the Etag. For production environments this horizontal responsibility might make more
	 * sense to be moved to a reverse proxy instead, so the request doesn't reach the service instance
	 * at all.
	 *
	 * Example:
	 *
	 * <pre>
	 * $ curl -H "Accept: application/json" \
	 *     -H 'If-None-Match: "..."' \
	 *     -i http://localhost:8081/api/v1/...
	 * </pre>
	 *
	 * <code>Etag</code>s are calculated from the {@link EntityModel#getVersion()} property.
	 */
	@Bean
	public ShallowEtagHeaderFilter shallowEtagHeaderFilter() {
		return new ShallowEtagHeaderFilter();
	}

}
