package it.laskaridis.payments.clearing.model;

import it.laskaridis.payments.bintable.BintableClient;
import it.laskaridis.payments.bintable.BintableData;
import it.laskaridis.payments.common.model.Money;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static it.laskaridis.payments.clearing.model.CardIssuerService.CREDIT_CARDS_CACHE;
import static it.laskaridis.payments.utils.Lang.not;
import static org.springframework.util.Assert.notNull;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClearingCostService {

    private static final String CLEARING_COSTS_CACHE = "clearingCosts";

    private final ClearingCostRepository clearingCostsRepository;
    private final CardIssuerService cardIssuerService;
    private final BintableClient bintableClient;

    /**
     * Returns all {@link ClearingCost}s.
     *
     * @return list of {@link ClearingCost}s
     */
    public List<ClearingCost> getClearingCostsRepository() {
        return this.clearingCostsRepository.findAll();
    }

    /**
     * Returns the {@link ClearingCost} for the specified card issuing country.
     *
     * @param country the issuing country for the card's {@link ClearingCost}
     * @return the {@link ClearingCost} for the specified card issuing country
     * @throws ClearingCostNotFoundException when not {@link ClearingCost} can be found
     * @throws IllegalArgumentException when specified card issuing country is null
     */
    @Transactional
    @Cacheable(CLEARING_COSTS_CACHE) // if present, retrieves cost item from cache
    public ClearingCost getClearingCost(final String country) {
        notNull(country, "country can't be null");

        return this.clearingCostsRepository
                .findByCardIssuingCountry(country)
                .orElseThrow(() -> new ClearingCostNotFoundException(country));
    }

    /**
     * Creates a new {@link ClearingCost}.
     *
     * @param country the {@link ClearingCost} record to create
     * @return the newly created {@link ClearingCost} instance
     * @throws IllegalArgumentException when specified clearing cost is null
     * @throws ClearingCostAlreadyExistsException when a clearing cost already exists for specified country
     */
    @Transactional
    @CachePut(value = CLEARING_COSTS_CACHE, key = "#country") // updates cache with new cost item
    public ClearingCost createClearingCost(final String country, final Money cost) {
        notNull(country, "country should not be null");

        if (this.clearingCostsRepository.findByCardIssuingCountry(country).isPresent()) {
            throw new ClearingCostAlreadyExistsException(country);
        }

        var clearingCostToCreate = new ClearingCost();
        clearingCostToCreate.setCardIssuingCountry(country);
        clearingCostToCreate.setClearingCost(cost);
        return this.clearingCostsRepository.save(clearingCostToCreate);
    }

    /**
     * Updates the cost amount of the {@link ClearingCost} for the specified card issuing
     * country.
     *
     * @param country the card issuing country of the {@link ClearingCost} to update
     * @param cost the new cost value
     * @return the updated {@link ClearingCost}
     * @throws ClearingCostNotFoundException when not {@link ClearingCost} can be found
     * @throws IllegalArgumentException when specified card issuing country or cost are null
     */
    @Transactional
    @CachePut(value = CLEARING_COSTS_CACHE, key = "#country") // updates cache with updated cost item
    public ClearingCost updateClearingCost(final String country, final Money cost) {
        notNull(country, "country can't be null");
        notNull(cost, "cost can't be null");

        var clearingCostToUpdate = this.clearingCostsRepository
                .findByCardIssuingCountryForUpdate(country)
                .orElseThrow(() -> new ClearingCostNotFoundException(country));

        clearingCostToUpdate.setClearingCost(cost);
        return this.clearingCostsRepository.save(clearingCostToUpdate);
    }

    /**
     * Deleted the {@link ClearingCost} of the specified card issuing country.
     *
     * @param country the card issuing country of the {@link ClearingCost} to delete
     * @return the deleted {@link ClearingCost}
     * @throws ClearingCostNotFoundException when not {@link ClearingCost} can be found
     * @throws IllegalArgumentException when specified card issuing country or cost are null
     */
    @Transactional
    @Caching(
        evict = {
            @CacheEvict(value = CLEARING_COSTS_CACHE, key = "#country"),
            // all associated credit cards will also be deleted so,
            // also evict all cached credit cards for consistency:
            @CacheEvict(value = CREDIT_CARDS_CACHE, allEntries = true)
        }
    )
    public ClearingCost deleteClearingCost(final String country) {
        notNull(country, "country can't be null");

        var clearingCostToDelete = this.clearingCostsRepository
                .findByCardIssuingCountryForUpdate(country)
                .orElseThrow(() -> new ClearingCostNotFoundException(country));

        this.clearingCostsRepository.delete(clearingCostToDelete);
        return clearingCostToDelete;
    }

    /**
     * Retrieves the {@link ClearingCost} for the {@link CardIssuer} with the
     * specified {@link PrimaryAccountNumber}. Essentially this method will try
     * to find the credit card's issuing country by looking up the credit card
     * details via a remote service. If one is found, it will then try to find
     * a {@link ClearingCost} entry for that country, which will be returned.
     *
     * This method will save the {@link CardIssuer} (i.e. only the issuer's
     * identification number) in the database to avoid sending future requests
     * to the remote service, for the same IIN.
     *
     * @param number the credit card PAN for which clearing costs should be retireved
     * @return clearing costs for the specified credit card
     * @throws CreditCardDetailsNotFoundException when no information can be found for
     *   the specified PAN
     * @throws ClearingCostNotFoundException when no clearing cost can be found for
     *   the card issuer's country
     */
    @Transactional
    public ClearingCost getClearingCost(final PrimaryAccountNumber number) {
        validate(number);

        final CardIssuer issuer;
        final String iin = number.getIssuerIdentificationNumber();

        final Optional<CardIssuer> possibleCardIssuer = this.cardIssuerService.getCardIssuer(iin);
        if (possibleCardIssuer.isPresent()) {
            issuer = possibleCardIssuer.get();
            return issuer.getClearingCost();
        } else {
            final var data = getCreditCardIssuerData(number);
            var country = data.getCountry().getCode();

            issuer = new CardIssuer();
            issuer.setBankCountryCode(country);
            issuer.setIssuerIdentificationNumber(iin);

            final var cost = this.clearingCostsRepository
                .findByCardIssuingCountry(country)
                .orElseThrow(() -> new ClearingCostNotFoundException(country));

            issuer.setClearingCost(cost);
            this.cardIssuerService.createCardIssuer(issuer);

            return cost;
        }
    }

    /**
     * Looks up the specified credit card's details from the remote service.
     *
     * @param number the credit card number to lookup
     * @return the credit card details
     * @throws CreditCardDetailsNotFoundException when details cannot be retrieved
     */
    private BintableData getCreditCardIssuerData(final PrimaryAccountNumber number) {
        try {
            return this.bintableClient.getCreditCardDetails(number.getIssuerIdentificationNumber());
        } catch (Throwable e) {
            log.debug(e.getMessage());
            throw new CreditCardDetailsNotFoundException(number);
        }
    }

    /**
     * Validates the specified credit card number.
     *
     * @param number the credit card number to validate
     * @throws ConstraintViolationException when invalid
     */
    private void validate(final PrimaryAccountNumber number) {
        final var validator = Validation.buildDefaultValidatorFactory().getValidator();
        final var errors = validator.validate(number);
        if (not(errors.isEmpty())) {
            throw new ConstraintViolationException(errors);
        }
    }
}
