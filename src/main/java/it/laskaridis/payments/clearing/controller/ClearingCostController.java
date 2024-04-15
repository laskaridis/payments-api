package it.laskaridis.payments.clearing.controller;

import it.laskaridis.payments.clearing.model.ClearingCost;
import it.laskaridis.payments.clearing.model.ClearingCostService;
import it.laskaridis.payments.clearing.view.json.CardClearingCostView;
import it.laskaridis.payments.clearing.view.json.CardNumberForm;
import it.laskaridis.payments.clearing.view.json.ClearingCostForm;
import it.laskaridis.payments.clearing.view.json.ClearingCostView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles requests for {@link ClearingCost} resources.
 */
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
class ClearingCostController {

    private final ClearingCostService service;

    @GetMapping("/clearing_costs")
    List<ClearingCostView> index() {
        return this.service.getClearingCostsRepository()
                .stream()
                .map(ClearingCostView::fromModel)
                .collect(Collectors.toList());
    }
    @GetMapping("/countries/{country}/clearing_cost")
    ResponseEntity<ClearingCostView> show(@PathVariable final String country) {

        final var model = this.service.getClearingCost(country);
        final var view = new ClearingCostView(model);

        return ResponseEntity
                .ok()
                .eTag(getEtagFor(model)) // allows for downstream caching
                .body(view);
    }

    private String getEtagFor(final ClearingCost model) {
        return String.valueOf(model.getVersion());
    }

    @PostMapping("/countries/{country}/clearing_cost")
    ResponseEntity<ClearingCostView> create(@PathVariable final String country,
                                            @RequestBody final ClearingCostForm form) {

        final var cost = form.toModel().getClearingCost();
        final var model = this.service.createClearingCost(country, cost);
        final var view = new ClearingCostView(model);
        final var location = new ClearingCostResource(model).getLocation();

        return ResponseEntity.created(location).body(view);
    }

    @PutMapping("/countries/{country}/clearing_cost")
    @PatchMapping("/countries/{country}/clearing_cost")
    ClearingCostView update(@PathVariable final String country,
                            @RequestBody final ClearingCostForm form) {

        final var cost = form.toModel().getClearingCost();
        final var model = this.service.updateClearingCost(country, cost);

        return new ClearingCostView(model);
    }

    @DeleteMapping("/countries/{country}/clearing_cost")
    public ResponseEntity<Void> delete(@PathVariable final String country) {
        this.service.deleteClearingCost(country);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/payment-cards-cost")
    CardClearingCostView getClearingCost(@RequestBody final CardNumberForm form) {
        final var model = this.service.getClearingCost(form.toModel());
        return new CardClearingCostView(model);
    }
}
