package de.threedimensions.blog.client.event;

import de.threedimensions.blog.client.model.BlogEntryJs;

/**
 * @author chris
 * 
 */
public class BlogEntryReceivedEvent implements Event<BlogEntryJs> {

    private final BlogEntryJs blogEntryJs;

    public BlogEntryReceivedEvent(BlogEntryJs blogEntryJs) {
	this.blogEntryJs = blogEntryJs;
    }

    @Override
    public BlogEntryJs getContent() {
	return blogEntryJs;
    }

}
