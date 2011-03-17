package de.threedimensions.blog.client;

/**
 * @author chris
 * 
 */
public interface EditorSupport {

    /**
     * Shows the editing area for editing or creating a new post
     */
    void showEditor();

    /**
     * Shows the blog area and hides the editor
     */
    void showBlog(String blogUrl);

}
