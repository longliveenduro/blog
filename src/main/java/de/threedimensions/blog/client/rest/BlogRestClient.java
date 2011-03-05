package de.threedimensions.blog.client.rest;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.http.client.RequestBuilder;

import de.threedimensions.blog.client.AsyncCallbackHandler;
import de.threedimensions.blog.client.model.BlogEntryJs;
import de.threedimensions.blog.client.model.BlogEntryRefJs;

/**
 * @author chris
 * 
 */
public class BlogRestClient {

    private static final String REST_BASE_URL = GWT.getHostPageBaseURL() + "app/";
    private static final String POSTS_URL = REST_BASE_URL + "posts";
    private static final String AUTH_URL = REST_BASE_URL + "auth";

    private RestRequestBuilder restRequestBuilder = new RestRequestBuilder();

    public void getPosts(final AsyncCallbackHandler asyncRestCallbackHandler) {
	RestResponseHandler restResponseHandler = new RestResponseHandler() {
	    @Override
	    public void handleRestResponse(RestResponse restResponse) {
		if (restResponse.isNotError()) {
		    JsArray<BlogEntryRefJs> blogEntryRefJs = buildBlogEntryRefsJs(restResponse.getResponse());
		    asyncRestCallbackHandler.listOfBlogEntriesReceived(blogEntryRefJs);
		} else {
		    asyncRestCallbackHandler.handleError(restResponse.getErrorMessage());
		}
	    }
	};
	restRequestBuilder.doRestCall(restResponseHandler, RequestBuilder.GET, POSTS_URL);
    }

    public void getBlogEntry(final String urlForPost, final AsyncCallbackHandler asyncRestCallbackHandler) {
	RestResponseHandler restResponseHandler = new RestResponseHandler() {
	    @Override
	    public void handleRestResponse(RestResponse restResponse) {
		if (restResponse.isNotError()) {
		    BlogEntryJs blogEntryJs = buildBlogEntryJs(restResponse.getResponse());
		    asyncRestCallbackHandler.blogEntryReceived(blogEntryJs);

		} else {
		    asyncRestCallbackHandler.handleError(restResponse.getErrorMessage());
		}
	    }
	};
	restRequestBuilder.doRestCall(restResponseHandler, RequestBuilder.GET, REST_BASE_URL + urlForPost);
    }

    public void prepareOpenIdLogin(final AsyncCallbackHandler asyncRestCallbackHandler) {
	RestResponseHandler restResponseHandler = new RestResponseHandler() {
	    @Override
	    public void handleRestResponse(RestResponse restResponse) {
		if (restResponse.isNotError()) {
		    asyncRestCallbackHandler.openIdLoginUrlReceived(restResponse.getResponse());
		} else {
		    asyncRestCallbackHandler.handleError(restResponse.getErrorMessage());
		}
	    }
	};
	restRequestBuilder.doRestCall(restResponseHandler, RequestBuilder.GET, AUTH_URL + "/openIdLogin");
    }

    private BlogEntryJs buildBlogEntryJs(String json) {
	JavaScriptObject javaScriptObject = asBlogEntryJs(json);
	return javaScriptObject.cast();
    }

    private JsArray<BlogEntryRefJs> buildBlogEntryRefsJs(String jsonArray) {

	asBlogEntryRefJs(jsonArray);
	JsArray<BlogEntryRefJs> jsArray = asBlogEntryRefJs(jsonArray);
	return jsArray;
    }

    private final native JsArray<BlogEntryRefJs> asBlogEntryRefJs(String jsonArray) /*-{ return eval(jsonArray); }-*/;

    /**
     * Convert the string of JSON into JavaScript object.
     */
    private final native JavaScriptObject asBlogEntryJs(String json) /*-{ return eval('(' + json + ')'); }-*/;
}
