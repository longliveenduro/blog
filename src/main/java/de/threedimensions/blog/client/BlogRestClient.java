package de.threedimensions.blog.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;

import de.threedimensions.blog.client.model.BlogEntryJs;
import de.threedimensions.blog.client.model.BlogEntryRefJs;

/**
 * @author chris
 * 
 */
public class BlogRestClient {

    private static final String REST_BASE_URL = GWT.getHostPageBaseURL() + "app/";
    private static final String POSTS_URL = REST_BASE_URL + "posts";

    public void getPosts(final AsyncRestCallbackHandler asyncRestCallbackHandler) {
	RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET, POSTS_URL);
	try {
	    requestBuilder.sendRequest("", new RequestCallback() {
		@Override
		public void onResponseReceived(Request request, Response response) {
		    if (response.getStatusCode() == 200) {
			JsArray<BlogEntryRefJs> blogEntryRefJs = buildBlogEntryRefsJs(response.getText());
			asyncRestCallbackHandler.listOfBlogEntriesReceived(blogEntryRefJs);
		    } else {
			asyncRestCallbackHandler
				.handleError("RequestException during rest call to get all post urls and hrefs. Http error "
					+ response.getStatusCode());
		    }
		}

		@Override
		public void onError(Request request, Throwable t) {
		    asyncRestCallbackHandler
			    .handleError("RequestException during rest call to get all post url and hrefs: "
				    + t.getMessage());
		}
	    });
	} catch (RequestException e) {
	    asyncRestCallbackHandler.handleError("RequestException during rest call to get all post url and hrefs: "
		    + e.getMessage());
	}
    }

    public void getBlogEntry(final String urlForPost, final AsyncRestCallbackHandler asyncRestCallbackHandler) {
	RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET, REST_BASE_URL + urlForPost);
	try {
	    requestBuilder.sendRequest("", new RequestCallback() {

		@Override
		public void onResponseReceived(Request request, Response response) {
		    if (response.getStatusCode() == 200) {
			BlogEntryJs blogEntryJs = buildBlogEntryJs(response.getText());
			asyncRestCallbackHandler.blogEntryReceived(blogEntryJs);
		    } else {
			asyncRestCallbackHandler.handleError("Couldn't access Rest service, http status code "
				+ response.getStatusCode());
		    }
		}

		@Override
		public void onError(Request request, Throwable exception) {
		    asyncRestCallbackHandler.handleError("Couldn't access Rest service for retrieval of post with url "
			    + urlForPost + ": " + exception.getMessage());
		}
	    });
	} catch (RequestException e) {
	    asyncRestCallbackHandler.handleError("Couldn't access Rest service for retrieval of post with id "
		    + urlForPost + ": " + e.getMessage());
	}
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
