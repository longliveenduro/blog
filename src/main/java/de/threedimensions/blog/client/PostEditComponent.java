package de.threedimensions.blog.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.threedimensions.blog.client.event.EventHandler;
import de.threedimensions.blog.client.event.PostCreatedEvent;
import de.threedimensions.blog.client.rest.BlogRestClient;
import de.threedimensions.blog.client.rest.ErrorHandler;

/**
 * @author chris
 * 
 */
public class PostEditComponent extends VerticalPanel implements ErrorHandler, EventHandler<PostCreatedEvent> {

    private final EditorSupport editorSupport;
    final Label errorFeedbackLabel = new Label();
    Button submit = new Button("Submit changes");
    private BlogRestClient blogRestClient = new BlogRestClient(this);

    public PostEditComponent(EditorSupport editorSupport) {
	this.editorSupport = editorSupport;

	final TextArea textArea = new TextArea();
	add(textArea);
	add(errorFeedbackLabel);
	add(submit);
	submit.addClickHandler(new ClickHandler() {
	    @Override
	    public void onClick(ClickEvent event) {
		errorFeedbackLabel.setText("Submitting blog entry...");
		blogRestClient.createNewPost(PostEditComponent.this, textArea.getText());
	    }
	});
    }

    /**
     * @see de.threedimensions.blog.client.rest.ErrorHandler#handleError(java.lang.String)
     */
    @Override
    public void handleError(String errorMessage) {
	errorFeedbackLabel.setText("Error creating new blog entry: " + errorMessage);
    }

    /**
     * @see de.threedimensions.blog.client.event.EventHandler#handleEvent(de.threedimensions.blog.client.event.Event)
     */
    @Override
    public void handleEvent(PostCreatedEvent event) {
	errorFeedbackLabel.setText("");
	editorSupport.showBlog(event.getContent());
    }
}
