package de.threedimensions.blog.client.rest;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestBuilder.Method;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;

/**
 * @author chris
 * 
 */
public class RestRequestBuilder {

    /**
     * Make a REST call to the given url with the given method with no content.
     * 
     * @param responseHandler
     * @param httpMethod
     * @param url
     */
    public void doRestCall(final RestResponseHandler responseHandler, final Method httpMethod, final String url) {
	doRestCall(responseHandler, httpMethod, url, "");
    }

    /**
     * Make a REST call to the given url with the given method.
     * 
     * @param responseHandler
     * @param httpMethod
     * @param url
     * @param content
     *            content sent as part of the request
     */
    public void doRestCall(final RestResponseHandler responseHandler, final Method httpMethod, final String url,
	    String content) {

	RequestBuilder requestBuilder = new RequestBuilder(httpMethod, url);
	try {
	    requestBuilder.sendRequest(content, new RequestCallback() {
		@Override
		public void onResponseReceived(Request request, Response response) {
		    if (response.getStatusCode() == 200) {
			responseHandler.handleRestResponse(new RestResponse(response.getText(), false));
		    } else {
			responseHandler.handleRestResponse(new RestResponse("Error calling '" + url + "', status code "
				+ response.getStatusCode() + ", text: " + response.getStatusText(), true));
		    }
		}

		@Override
		public void onError(Request request, Throwable t) {
		    responseHandler.handleRestResponse(new RestResponse(t.getClass() + " during rest call to url '"
			    + url + "': " + t.getMessage(), true));
		}
	    });
	} catch (RequestException e) {
	    responseHandler.handleRestResponse(new RestResponse(e.getClass() + " during rest call to url '" + url
		    + "': " + e.getMessage(), true));
	}
    }
}
