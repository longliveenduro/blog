package de.threedimensions.blog.client;

import com.google.gwt.core.client.JsArray;

import de.threedimensions.blog.client.model.BlogEntryJs;
import de.threedimensions.blog.client.model.BlogEntryRefJs;

/**
 * @author chris
 * 
 */
public interface AsyncRestCallbackHandler {

    /**
     * @param blogEntryJs
     */
    void blogEntryReceived(BlogEntryJs blogEntryJs);

    /**
     * @param string
     */
    void handleError(String errorMessage);

    /**
     * @param blogEntryRefJs
     */
    void listOfBlogEntriesReceived(JsArray<BlogEntryRefJs> blogEntryRefJs);

}
