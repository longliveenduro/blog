package de.threedimensions.blog.client.event;

/**
 * @author chris
 * @param <T>
 *            subclass of {@link Event}
 * 
 */
public interface EventHandler<T extends Event> {
    void handleEvent(T event);
}
