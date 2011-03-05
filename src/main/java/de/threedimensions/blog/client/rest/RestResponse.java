package de.threedimensions.blog.client.rest;

/**
 * @author chris
 * 
 */
public class RestResponse {

    private final String errorMessage;
    private final String response;

    /**
     * @param errorMessage
     * @param response
     */
    public RestResponse(String message, boolean isError) {
	if (isError) {
	    this.errorMessage = message;
	    this.response = null;
	} else {
	    response = message;
	    errorMessage = null;
	}
    }

    public boolean isError() {
	return errorMessage != null;
    }

    public boolean isNotError() {
	return response != null;
    }

    /**
     * @return the errorMessage
     */
    public String getErrorMessage() {
	return errorMessage;
    }

    /**
     * @return the response
     */
    public String getResponse() {
	return response;
    }

}
