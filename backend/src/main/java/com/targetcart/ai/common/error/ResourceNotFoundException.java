package com.targetcart.ai.common.error;

/**
 * Thrown when a requested resource does not exist.
 * Mapped to HTTP 404 Not Found by the global exception handler.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public static ResourceNotFoundException of(String resource, Long id) {
        return new ResourceNotFoundException(resource + " not found with id " + id);
    }
}