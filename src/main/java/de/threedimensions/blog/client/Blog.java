package de.threedimensions.blog.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.threedimensions.blog.client.model.BlogEntryJs;
import de.threedimensions.blog.client.model.BlogEntryRefJs;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Blog implements EntryPoint, AsyncRestCallbackHandler {

    private VerticalPanel contentMiddlePanel = new VerticalPanel();
    private BlogRestClient blogRestClient = new BlogRestClient();
    private VerticalPanel navPanel = new VerticalPanel();
    private Label feedbackLabel = new Label();

    /**
     * Entry point method.
     */
    public void onModuleLoad() {
	HorizontalPanel mainPanel = new HorizontalPanel();
	feedbackLabel.setStyleName("errorFeedback", true);
	contentMiddlePanel.add(feedbackLabel);
	mainPanel.add(contentMiddlePanel);
	mainPanel.add(navPanel);
	RootPanel.get("blogPanel").add(mainPanel);

	blogRestClient.getPosts(this);
    }

    @Override
    public void blogEntryReceived(BlogEntryJs blogEntryJs) {
	Widget widget = new HTML("<h2>" + blogEntryJs.getHeading() + "</h2>" + blogEntryJs.getContent());
	contentMiddlePanel.add(widget);
	contentMiddlePanel.add(new CommentPanel(blogEntryJs.getId()));
    }

    @Override
    public void listOfBlogEntriesReceived(JsArray<BlogEntryRefJs> blogEntryRefJsArray) {
	for (int i = 0; i < blogEntryRefJsArray.length(); i++) {
	    BlogEntryRefJs blogEntryRefJs = blogEntryRefJsArray.get(i);
	    blogRestClient.getBlogEntry(blogEntryRefJs.getUrl(), this);
	    navPanel.add(new Hyperlink("Post " + blogEntryRefJs.getId(), blogEntryRefJs.getUrl()));
	}
    }

    @Override
    public void handleError(String errorMessage) {
	feedbackLabel.setText(errorMessage);
    }
}
