package de.threedimensions.blog.client.rest;

/**
 * @author chris
 * 
 */
public interface ErrorHandler {

    /**
     * @param errorMessage
     */
    void handleError(String errorMessage);
}
