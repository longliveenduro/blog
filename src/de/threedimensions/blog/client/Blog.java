package de.threedimensions.blog.client;

import java.util.Date;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.threedimensions.blog.shared.BlogEntry;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Blog implements EntryPoint {

    /**
     * Entry point method.
     */
    public void onModuleLoad() {
	HorizontalPanel mainPanel = new HorizontalPanel();

	VerticalPanel contentMiddlePanel = new VerticalPanel();
	contentMiddlePanel.add(getCurrentBlogEntry());
	mainPanel.add(contentMiddlePanel);

	HorizontalPanel commentPanel = createCommentPanel();
	contentMiddlePanel.add(commentPanel);

	VerticalPanel navPanel = createNavPanel();
	mainPanel.add(navPanel);

	RootPanel.get("blogPanel").add(mainPanel);
    }

    /**
     * @return
     */
    private Widget getCurrentBlogEntry() {
	BlogEntry blogEntry = new BlogEntry(
		"Heading 1",
		"Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.",
		new Date());
	return new HTML("<h2>" + blogEntry.getHeading() + "</h2>" + blogEntry.getContent());
    }

    private VerticalPanel createNavPanel() {
	VerticalPanel navPanel = new VerticalPanel();
	navPanel.add(new Hyperlink("Post 1", "post1"));
	navPanel.add(new Hyperlink("Post 2", "post2"));
	return navPanel;
    }

    private HorizontalPanel createCommentPanel() {
	HorizontalPanel commentPanel = new HorizontalPanel();
	final TextArea commentInputTextArea = new TextArea();
	commentInputTextArea.setVisibleLines(1);
	commentInputTextArea.addFocusHandler(new FocusHandler() {
	    @Override
	    public void onFocus(FocusEvent event) {
		commentInputTextArea.setVisibleLines(5);
	    }
	});
	commentInputTextArea.addBlurHandler(new BlurHandler() {

	    @Override
	    public void onBlur(BlurEvent event) {
		commentInputTextArea.setVisibleLines(1);
	    }
	});
	commentPanel.add(commentInputTextArea);

	Button commentButton = new Button("Post a comment", new ClickHandler() {
	    public void onClick(ClickEvent event) {
		commentInputTextArea.setVisible(true);
	    }
	});
	commentPanel.add(commentButton);
	return commentPanel;
    }
}
