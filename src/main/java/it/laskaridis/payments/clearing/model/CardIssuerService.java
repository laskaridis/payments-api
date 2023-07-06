package it.laskaridis.payments.clearing.model;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class CardIssuerService {

    public static final String CREDIT_CARDS_CACHE = "creditCards";
    private final CardIssuerRepository repository;

    public CardIssuerService(CardIssuerRepository repository) {
        this.repository = repository;
    }

    @Cacheable(CREDIT_CARDS_CACHE)
    public Optional<CardIssuer> getCardIssuer(String iin) {
        return this.repository.findByIssuerIdentificationNumber(iin);
    }

    @CacheEvict(value = CREDIT_CARDS_CACHE, key = "#iin")
    public CardIssuer deleteCreditCard(String iin) {
        CardIssuer cardToDelete = getCardIssuer(iin)
                .orElseThrow(() -> new CardIssuerNotFoundException(iin));

        this.repository.delete(cardToDelete);
        return cardToDelete;
    }

    @CachePut(value = CREDIT_CARDS_CACHE, key = "#card.issuerIdentificationNumber")
    public CardIssuer createCardIssuer(CardIssuer card) {
        String iin = card.getIssuerIdentificationNumber();
        if (this.repository.findByIssuerIdentificationNumber(iin).isPresent()) {
            throw new CardIssuerAlreadyExistsException(iin);
        }

        return this.repository.save(card);
    }
}
