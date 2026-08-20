package com.targetcart.ai.common.error;

import java.time.Instant;
import java.util.List;

import org.springframework.http.HttpStatus;

/**
 * Reusable error payload returned for every failed API request.
 *
 * <p>Deliberately contains no stack traces or database internals so clients
 * never see implementation details.
 */
public record ApiError(
        Instant timestamp,
        int status,
        String error,
        String message,
        String path,
        List<FieldErrorDetail> fieldErrors) {

    public record FieldErrorDetail(String field, String message) {
    }

    public static ApiError of(HttpStatus status, String message, String path) {
        return new ApiError(Instant.now(), status.value(), status.getReasonPhrase(), message, path, List.of());
    }

    public static ApiError withFieldErrors(HttpStatus status, String message, String path,
                                           List<FieldErrorDetail> fieldErrors) {
        return new ApiError(Instant.now(), status.value(), status.getReasonPhrase(), message, path, fieldErrors);
    }
}