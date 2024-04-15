package it.laskaridis.payments.clearing.model;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CardIssuerService {

    public static final String CREDIT_CARDS_CACHE = "creditCards";
    private final CardIssuerRepository repository;

    @Cacheable(CREDIT_CARDS_CACHE)
    public Optional<CardIssuer> getCardIssuer(final String iin) {
        return this.repository.findByIssuerIdentificationNumber(iin);
    }

    @CacheEvict(value = CREDIT_CARDS_CACHE, key = "#iin")
    public CardIssuer deleteCreditCard(final String iin) {
        var cardToDelete = getCardIssuer(iin).orElseThrow(() -> new CardIssuerNotFoundException(iin));
        this.repository.delete(cardToDelete);
        return cardToDelete;
    }

    @CachePut(value = CREDIT_CARDS_CACHE, key = "#card.issuerIdentificationNumber")
    public CardIssuer createCardIssuer(final CardIssuer card) {
        var iin = card.getIssuerIdentificationNumber();
        if (this.repository.findByIssuerIdentificationNumber(iin).isPresent()) {
            throw new CardIssuerAlreadyExistsException(iin);
        }
        return this.repository.save(card);
    }
}
