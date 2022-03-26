package ru.fit.nsu.np.openmap.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class ControllerAdvice {

    private HttpHeaders headers;

    @PostConstruct
    protected void init() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<Pair<String, String>> errors = new LinkedList<>();
        addErrors(errors, bindingResult.getFieldErrors());
        addErrors(errors, bindingResult.getGlobalErrors());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .headers(headers)
                .body(new ErrorResponse(errors));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException e) {
        return handleThrowable(e);
    }

    @ExceptionHandler(Error.class)
    public ResponseEntity<ErrorResponse> handleError(Error e) {
        return handleThrowable(e);
    }

    private ResponseEntity<ErrorResponse> handleThrowable(Throwable t) {
        String message = t.getMessage();
        if (StringUtils.isBlank(message)) {
            Throwable cause = t.getCause();
            if (cause == null) {
                message = "Internal server error";
            } else {
                message = cause.getMessage();
                if (StringUtils.isBlank(message)) {
                    message = "Internal server error";
                }
            }
        }

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .headers(headers)
                .body(new ErrorResponse("unknown", message));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .headers(headers)
                .body(new ErrorResponse("unknown", "Request body is incorrect"));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .headers(headers)
                .body(new ErrorResponse(e.getConstraintName(), e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .headers(headers)
                .body(new ErrorResponse(e.getName(), e.getMessage()));
    }

    private void addErrors(List<Pair<String, String>> target, List<? extends ObjectError> source) {
        for (ObjectError error : source) {
            target.add(Pair.of(error.getObjectName(), error.getDefaultMessage()));
        }
    }
}
