package de.threedimensions.blog.server.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.threedimensions.blog.server.model.User;

/**
 * @author chris
 * 
 */
public class UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * @param user
     */
    public void save(User user) {
	entityManager.persist(user);
    }

}
