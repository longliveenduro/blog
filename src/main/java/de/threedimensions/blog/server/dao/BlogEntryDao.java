package de.threedimensions.blog.server.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;

import de.threedimensions.blog.server.model.BlogEntry;

/**
 * @author chris
 * 
 */
@Repository
public class BlogEntryDao {

    @Autowired
    private ObjectifyFactory objectifyFactory;

    public BlogEntry loadBlogEntryById(Long postId) {
	Objectify objectify = objectifyFactory.begin();
	BlogEntry blogEntry = objectify.find(BlogEntry.class, postId);
	return blogEntry;
    }

}
