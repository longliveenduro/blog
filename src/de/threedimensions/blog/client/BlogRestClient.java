package de.threedimensions.blog.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;

import de.threedimensions.blog.client.model.BlogEntryJs;

/**
 * @author chris
 * 
 */
public class BlogRestClient {

    private static final String REST_URL = GWT.getHostPageBaseURL() + "app/posts";

    public void getCurrentBlogEntry(final AsyncRestCallbackHandler asyncRestCallbackHandler) {
	RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET, REST_URL);
	try {
	    requestBuilder.sendRequest("", new RequestCallback() {

		@Override
		public void onResponseReceived(Request request, Response response) {
		    if (response.getStatusCode() == 200) {
			BlogEntryJs blogEntryJs = buildBlogEntryJs(response.getText());
			asyncRestCallbackHandler.blogEntryReceived(blogEntryJs);
		    }
		    asyncRestCallbackHandler.handleError("Couldn't access Rest service, http status code "
			    + response.getStatusCode());
		}

		@Override
		public void onError(Request request, Throwable exception) {
		    throw new RuntimeException("Couldn't access Rest service", exception);
		}
	    });
	} catch (RequestException e) {
	    asyncRestCallbackHandler.handleError("RequestException during rest call: " + e.getMessage());
	}
    }

    private BlogEntryJs buildBlogEntryJs(String json) {
	JavaScriptObject javaScriptObject = asBlogEntryJs(json);
	return javaScriptObject.cast();
    }

    /**
     * Convert the string of JSON into JavaScript object.
     */
    private final native JavaScriptObject asBlogEntryJs(String json) /*-{ return eval('(' + json + ')'); }-*/;
}
