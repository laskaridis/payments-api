package it.laskaridis.payments.clearing.model;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClearingCostRepository extends JpaRepository<ClearingCost, UUID> {

    Optional<ClearingCost> findByCardIssuingCountry(String country);

    /**
     * Runs the same query as {@link #findByCardIssuingCountry(String)} and in addition
     * acquires an exclusive lock (i.e. "for [no key] update") on the returned record.
     *
     * Meant to be used in operations that intend to mutate tha returned record (update
     * or delete), or intend to take decisions based on the returned record's state
     * which need to remain true (i.e. invariants) throughout the transaction, such as
     * "check-then-act" operations.
     *
     * @param country the issuers country code
     * @return the {@link ClearingCost} for the specified country code, if one exists.
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    // required because of the "ForUpdate" method postfix (query would not compile otherwise):
    @Query("select c from ClearingCost c where c.cardIssuingCountry = :country")
    Optional<ClearingCost> findByCardIssuingCountryForUpdate(String country);
}
