package de.threedimensions.blog.client.event;

/**
 * @param <T>
 *            content of the event
 * 
 * @author chris
 */
public interface Event<T> {

    T getContent();
}
