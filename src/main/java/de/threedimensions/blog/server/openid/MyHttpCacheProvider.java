package de.threedimensions.blog.server.openid;

import org.openid4java.util.HttpFetcher;

import com.google.appengine.api.urlfetch.URLFetchServiceFactory;
import com.google.inject.Provider;

public class MyHttpCacheProvider implements Provider<HttpFetcher> {

    @Override
    public HttpFetcher get() {
	return new Openid4javaFetcher(URLFetchServiceFactory.getURLFetchService());
    }
}