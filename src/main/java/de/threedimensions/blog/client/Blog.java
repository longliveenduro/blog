package de.threedimensions.blog.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

import de.threedimensions.blog.client.components.BlogEntryComponent;
import de.threedimensions.blog.client.components.Navbar;
import de.threedimensions.blog.client.event.EventHandler;
import de.threedimensions.blog.client.event.ListOfBlogEntryRefsReceivedEvent;
import de.threedimensions.blog.client.model.BlogEntryRefJs;
import de.threedimensions.blog.client.rest.BlogRestClient;
import de.threedimensions.blog.client.rest.ErrorHandler;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Blog implements EntryPoint, ErrorHandler, EventHandler<ListOfBlogEntryRefsReceivedEvent>, EditorSupport {

    private BlogRestClient blogRestClient = new BlogRestClient(this);
    private Label feedbackLabel = new Label();
    private BlogEntryComponent blogEntryComponent;
    private PostEditComponent postEditPanel = new PostEditComponent(this);

    /**
     * Entry point method.
     */
    public void onModuleLoad() {
	Navbar navbar = new Navbar(blogRestClient, this);
	RootPanel.get("navbar").add(navbar);

	blogEntryComponent = new BlogEntryComponent();
	getPanelForBlogEntry().add(blogEntryComponent);
	blogRestClient.getPosts(this);
    }

    private RootPanel getPanelForBlogEntry() {
	return RootPanel.get("blogEntryComponent");
    }

    @Override
    public void handleError(String errorMessage) {
	feedbackLabel.setText(errorMessage);
    }

    @Override
    public void handleEvent(ListOfBlogEntryRefsReceivedEvent event) {
	BlogEntryRefJs blogEntryRefJs = event.getContent().get(0);
	if (blogEntryRefJs != null) {
	    blogRestClient.getBlogEntry(blogEntryRefJs.getUrl(), blogEntryComponent);
	}
    }

    /**
     * @see de.threedimensions.blog.client.EditorSupport#showEditor()
     */
    @Override
    public void showEditor() {
	getPanelForBlogEntry().remove(blogEntryComponent);
	getPanelForBlogEntry().add(postEditPanel);
    }

    /**
     * @see de.threedimensions.blog.client.EditorSupport#showBlog()
     */
    @Override
    public void showBlog(String url) {
	getPanelForBlogEntry().remove(postEditPanel);
	getPanelForBlogEntry().add(blogEntryComponent);
	blogRestClient.getBlogEntry(url, blogEntryComponent);
    }
}
