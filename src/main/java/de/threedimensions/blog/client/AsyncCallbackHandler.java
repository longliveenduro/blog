package de.threedimensions.blog.client;

import com.google.gwt.core.client.JsArray;

import de.threedimensions.blog.client.model.BlogEntryRefJs;

/**
 * @author chris
 * 
 */
public interface AsyncCallbackHandler {

    /**
     * @param blogEntryRefJs
     */
    void listOfBlogEntriesReceived(JsArray<BlogEntryRefJs> blogEntryRefJs);
}
