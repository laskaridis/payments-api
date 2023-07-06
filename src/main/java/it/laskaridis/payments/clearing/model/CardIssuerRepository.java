package it.laskaridis.payments.clearing.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CardIssuerRepository extends JpaRepository<CardIssuer, UUID> {

    Optional<CardIssuer> findByIssuerIdentificationNumber(String iin);
}
