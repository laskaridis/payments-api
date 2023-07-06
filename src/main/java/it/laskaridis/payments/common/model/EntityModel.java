package it.laskaridis.payments.common.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.UUID;


/**
 * Base class which must be extended from all domain entities in the application.
 * Its intention is to provide fields and annotations which are common to all domain
 * entities, thereby reducing duplication and promoting consistency.
 */
@MappedSuperclass
@Data
@NoArgsConstructor // required for JPA
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class EntityModel {

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private UUID id;

    @Version
    private Long version;

    @CreatedDate
    @Column(name = "created_at")
    @NotNull
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "last_modified_at")
    @NotNull
    private LocalDateTime lastModifiedAt;

    public EntityModel(UUID id) {
        Assert.notNull(id, "id can't be null");
        this.id = id;
    }

}
