package it.laskaridis.payments.clearing.controller;

import it.laskaridis.payments.clearing.model.ClearingCost;
import it.laskaridis.payments.clearing.model.ClearingCostService;
import it.laskaridis.payments.clearing.view.json.*;
import it.laskaridis.payments.common.model.Money;
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
public class ClearingCostController {

    private final ClearingCostService service;

    public ClearingCostController(ClearingCostService clearingCosts) {
        this.service = clearingCosts;
    }

    @GetMapping("/clearing_costs")
    public List<ClearingCostView> index() {
        return this.service.getClearingCostsRepository()
                .stream()
                .map(ClearingCostView::fromModel)
                .collect(Collectors.toList());
    }
    @GetMapping("/countries/{country}/clearing_cost")
    public ResponseEntity<ClearingCostView> show(@PathVariable final String country) {

        var model = this.service.getClearingCost(country);
        var view = new ClearingCostView(model);

        return ResponseEntity
                .ok()
                .eTag(getEtagFor(model)) // allows for downstream caching
                .body(view);
    }

    private String getEtagFor(ClearingCost model) {
        return String.valueOf(model.getVersion());
    }

    @PostMapping("/countries/{country}/clearing_cost")
    public ResponseEntity<ClearingCostView> create(@PathVariable final String country,
                                                   @RequestBody final ClearingCostForm form) {

        var cost = form.toModel().getClearingCost();
        var model = this.service.createClearingCost(country, cost);
        var view = new ClearingCostView(model);
        var location = new ClearingCostResource(model).getLocation();

        return ResponseEntity.created(location).body(view);
    }

    @PutMapping("/countries/{country}/clearing_cost")
    @PatchMapping("/countries/{country}/clearing_cost")
    public ClearingCostView update(@PathVariable final String country,
                                   @RequestBody final ClearingCostForm form) {

        Money cost = form.toModel().getClearingCost();
        var model = this.service.updateClearingCost(country, cost);

        return new ClearingCostView(model);
    }

    @DeleteMapping("/countries/{country}/clearing_cost")
    public ResponseEntity<Void> delete(@PathVariable final String country) {
        this.service.deleteClearingCost(country);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/payment-cards-cost")
    public CardClearingCostView getClearingCost(@RequestBody CardNumberForm form) {
        var model = this.service.getClearingCost(form.toModel());
        return new CardClearingCostView(model);
    }

}
