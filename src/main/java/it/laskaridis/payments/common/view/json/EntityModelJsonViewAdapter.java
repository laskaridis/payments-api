package it.laskaridis.payments.common.view.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import it.laskaridis.payments.common.model.EntityModel;

import java.time.LocalDateTime;
import java.util.UUID;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

public class EntityModelJsonViewAdapter<T extends EntityModel> extends JsonViewAdapter<T> {

    public EntityModelJsonViewAdapter(final T model) {
        super(model);
    }

    @Schema(description = "Record's unique identifier", requiredMode = REQUIRED)
    @JsonProperty("id")
    public UUID getId() {
        return getModel().getId();
    }

    @Schema(description = "Monotonically increasing version identifier",
            requiredMode = REQUIRED)
    @JsonProperty("version")
    public Long getVersion() {
        return getModel().getVersion();
    }

    @Schema(description = "Creation timestamp", requiredMode = REQUIRED)
    @JsonProperty("created_at")
    public LocalDateTime getCreatedAt() {
        return getModel().getCreatedAt();
    }

    @Schema(description = "Last modification timestamp", requiredMode = REQUIRED)
    @JsonProperty("last_modified_at")
    public LocalDateTime getLastModifiedAt() {
        return getModel().getLastModifiedAt();
    }

    // TODO: implement equals and hash code

}
