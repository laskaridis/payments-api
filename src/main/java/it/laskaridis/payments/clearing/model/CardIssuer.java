package it.laskaridis.payments.clearing.model;

import it.laskaridis.payments.common.model.EntityModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@NoArgsConstructor // required from JPA
@Entity
@Table(name = "card_issuers")
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper = true)
public class CardIssuer extends EntityModel {

    @Column(name = "issuer_identification_number")
    @NotNull @Length(min = 6, max = 6)
    private String issuerIdentificationNumber;

    @Column(name = "bank_country_code")
    @NotNull @Length(min = 2, max = 2)
    private String bankCountryCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private ClearingCost clearingCost;

}
