package de.threedimensions.blog.client.event;

/**
 * Event for successful creation of a new blog post.
 * 
 * @author chris
 */
public class PostCreatedEvent implements Event<String> {

    private final String urlOfNewPost;

    /**
     * @param urlOfNewPost
     */
    public PostCreatedEvent(String urlOfNewPost) {
	this.urlOfNewPost = urlOfNewPost;
    }

    /**
     * @return url for newly created post.
     * @see de.threedimensions.blog.client.event.Event#getContent()
     */
    @Override
    public String getContent() {
	return urlOfNewPost;
    }

}
