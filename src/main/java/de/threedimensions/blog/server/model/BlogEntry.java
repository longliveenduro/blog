package de.threedimensions.blog.server.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.joda.time.DateTime;

/**
 * @author Chris Wewerka
 * 
 */
@Entity
public class BlogEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String heading;
    private String content;
    private DateTime creationTime;

    /**
     * @param heading
     * @param content
     * @param creationTime
     */
    public BlogEntry(String heading, String content, DateTime creationTime) {
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
    public DateTime getCreationTime() {
	return creationTime;
    }

    /**
     * @param creationTime
     *            the creationTime to set
     */
    public void setCreationTime(DateTime creationTime) {
	this.creationTime = creationTime;
    }

    /**
     * @return the id
     */
    public Long getId() {
	return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Long id) {
	this.id = id;
    }

}
