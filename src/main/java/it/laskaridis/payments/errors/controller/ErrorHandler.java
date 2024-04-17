package it.laskaridis.payments.errors.controller;

import it.laskaridis.payments.errors.model.ResourceAlreadyTakenException;
import it.laskaridis.payments.errors.model.ResourceNotFoundException;
import it.laskaridis.payments.errors.view.ClientErrorView;
import it.laskaridis.payments.errors.view.ClientValidationErrorView;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler({ ResourceNotFoundException.class })
    public ResponseEntity<ClientErrorView> handleNotFoundException(
            final ResourceNotFoundException e,
            final WebRequest request) {

        log.debug(e.getMessage());
        return ResponseEntity.status(NOT_FOUND).body(ClientErrorView.from(e));
    }

    @ExceptionHandler({ ResourceAlreadyTakenException.class })
    public ResponseEntity<ClientErrorView> handleNotFoundException(
            final ResourceAlreadyTakenException e,
            final WebRequest request) {

        log.debug(e.getMessage());
        return ResponseEntity.status(UNPROCESSABLE_ENTITY).body(ClientErrorView.from(e));
    }

    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<List<ClientValidationErrorView>> handleConstraintViolationException(
            final ConstraintViolationException e,
            final WebRequest request) {

        log.debug(e.getMessage());
        final var errors = ClientValidationErrorView.from(e.getConstraintViolations());
        return ResponseEntity.status(UNPROCESSABLE_ENTITY).body(errors);
    }

    @ExceptionHandler({ UnsupportedOperationException.class })
    public ResponseEntity<Void> handleUnsupportedOperatorException(
            final UnsupportedOperationException e,
            final WebRequest request) {

        log.debug(e.getMessage());
        return ResponseEntity.status(NOT_IMPLEMENTED).build();
    }
}
