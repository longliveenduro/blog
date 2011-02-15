package de.threedimensions.blog.client;

import de.threedimensions.blog.client.model.BlogEntryJs;

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

}
