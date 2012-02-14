package de.threedimensions.blog.server.dto;

import java.util.UUID;

/**
 * @author chris
 * 
 */
public class BlogEntryRef {
    private final UUID id;
    private final String title;
    private final String url;

    /**
     * @param id
     * @param url
     */
    public BlogEntryRef(UUID id, String title, String url) {
	this.id = id;
	this.title = title;
	this.url = url;
    }

    /**
     * @return the title
     */
    public String getTitle() {
	return title;
    }

    /**
     * @return the id
     */
    public UUID getId() {
	return id;
    }

    /**
     * @return the url
     */
    public String getUrl() {
	return url;
    }

}
