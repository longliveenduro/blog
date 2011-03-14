package de.threedimensions.blog.server.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import de.threedimensions.blog.server.model.BlogEntry;

/**
 * @author chris
 * 
 */
@Repository
public class BlogEntryDao {

    @PersistenceContext
    private EntityManager em;

    public BlogEntry loadBlogEntryById(Long postId) {
	BlogEntry blogEntry = em.find(BlogEntry.class, postId);
	return blogEntry;
    }

}
