package de.threedimensions.blog.client.event;

import com.google.gwt.core.client.JsArray;

import de.threedimensions.blog.client.model.BlogEntryRefJs;

/**
 * @author chris
 * 
 */
public class ListOfBlogEntryRefsReceivedEvent implements Event<JsArray<BlogEntryRefJs>> {

    private final JsArray<BlogEntryRefJs> blogEntryRefsJs;

    /**
     * @param blogEntryRefJs
     */
    public ListOfBlogEntryRefsReceivedEvent(JsArray<BlogEntryRefJs> blogEntryRefsJs) {
	this.blogEntryRefsJs = blogEntryRefsJs;
    }

    @Override
    public JsArray<BlogEntryRefJs> getContent() {
	return blogEntryRefsJs;
    }

}
