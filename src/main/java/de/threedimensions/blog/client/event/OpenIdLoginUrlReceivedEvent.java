package de.threedimensions.blog.client.event;

/**
 * @author chris
 * 
 */
public class OpenIdLoginUrlReceivedEvent implements Event<String> {

    private final String url;

    /**
     * @param url
     */
    public OpenIdLoginUrlReceivedEvent(String url) {
	this.url = url;
    }

    @Override
    public String getContent() {
	return url;
    }

}
