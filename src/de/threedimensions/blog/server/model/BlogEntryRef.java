package de.threedimensions.blog.server.model;

/**
 * @author chris
 * 
 */
public class BlogEntryRef {
    private final Long id;
    private final String url;

    /**
     * @param id
     * @param url
     */
    public BlogEntryRef(Long id, String url) {
	this.id = id;
	this.url = url;
    }

    /**
     * @return the id
     */
    public Long getId() {
	return id;
    }

    /**
     * @return the url
     */
    public String getUrl() {
	return url;
    }

}
