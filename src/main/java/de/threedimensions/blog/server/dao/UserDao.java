package de.threedimensions.blog.server.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;

import de.threedimensions.blog.server.model.User;

/**
 * @author chris
 * 
 */
@Repository
public class UserDao {

    @Autowired
    private ObjectifyFactory objectifyFactory;

    /**
     * @param user
     */
    public void save(User user) {
	Objectify objectify = objectifyFactory.begin();
	objectify.put(user);
    }

}
