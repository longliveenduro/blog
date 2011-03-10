package de.threedimensions.blog.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.threedimensions.blog.client.components.BlogEntryComponent;
import de.threedimensions.blog.client.components.Navbar;
import de.threedimensions.blog.client.model.BlogEntryRefJs;
import de.threedimensions.blog.client.rest.BlogRestClient;
import de.threedimensions.blog.client.rest.ErrorHandler;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Blog implements EntryPoint, AsyncCallbackHandler, ErrorHandler {

    private VerticalPanel contentMiddlePanel = new VerticalPanel();
    private BlogRestClient blogRestClient = new BlogRestClient(this);
    private VerticalPanel navPanel = new VerticalPanel();
    private Label feedbackLabel = new Label();
    private BlogEntryComponent blogEntryComponent;

    /**
     * Entry point method.
     */
    public void onModuleLoad() {
	HorizontalPanel mainPanel = new HorizontalPanel();
	feedbackLabel.setStyleName("errorFeedback", true);
	contentMiddlePanel.add(feedbackLabel);

	mainPanel.add(contentMiddlePanel);
	mainPanel.add(navPanel);
	// RootPanel.get("blogPanel").add(mainPanel);

	Navbar navbar = new Navbar(blogRestClient);
	RootPanel.get("navbar").add(navbar);

	blogEntryComponent = new BlogEntryComponent();
	RootPanel.get("blogEntryComponent").add(blogEntryComponent);
	blogRestClient.getPosts(this);
    }

    @Override
    public void listOfBlogEntriesReceived(JsArray<BlogEntryRefJs> blogEntryRefJsArray) {
	for (int i = 0; i < blogEntryRefJsArray.length(); i++) {
	    BlogEntryRefJs blogEntryRefJs = blogEntryRefJsArray.get(i);
	    blogRestClient.getBlogEntry(blogEntryRefJs.getUrl(), blogEntryComponent);
	    navPanel.add(new Hyperlink("Post " + blogEntryRefJs.getId(), blogEntryRefJs.getUrl()));
	}
    }

    @Override
    public void handleError(String errorMessage) {
	feedbackLabel.setText(errorMessage);
    }
}
