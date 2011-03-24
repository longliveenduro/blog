package de.threedimensions.blog.server.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author chris
 * 
 */
@Entity
public class User {

    @Id
    private String uuid;

    private User() {
    }

    /**
     * @param uuidForUser
     */
    public User(UUID uuidForUser) {
	this.uuid = uuidForUser.toString();
    }

}
