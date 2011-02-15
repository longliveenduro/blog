package de.threedimensions.blog.server.model;

import java.util.Date;

/**
 * @author Chris Wewerka
 * 
 */
public class BlogEntry {
    private String heading;
    private String content;
    private Date creationTime;

    /**
     * @param heading
     * @param content
     * @param creationTime
     */
    public BlogEntry(String heading, String content, Date creationTime) {
	super();
	this.heading = heading;
	this.content = content;
	this.creationTime = creationTime;
    }

    /**
     * @return the heading
     */
    public String getHeading() {
	return heading;
    }

    /**
     * @param heading
     *            the heading to set
     */
    public void setHeading(String heading) {
	this.heading = heading;
    }

    /**
     * @return the content
     */
    public String getContent() {
	return content;
    }

    /**
     * @param content
     *            the content to set
     */
    public void setContent(String content) {
	this.content = content;
    }

    /**
     * @return the creationTime
     */
    public Date getCreationTime() {
	return creationTime;
    }

    /**
     * @param creationTime
     *            the creationTime to set
     */
    public void setCreationTime(Date creationTime) {
	this.creationTime = creationTime;
    }

}
