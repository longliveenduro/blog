package de.threedimensions.blog.client;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextArea;

import de.threedimensions.blog.client.model.BlogEntryJs;

/**
 * Comment panel for a specific {@link BlogEntryJs}.
 * 
 * @author chris
 * 
 */
public class CommentPanel extends HorizontalPanel {

    private final Integer postId;

    /**
     * @param postId
     *            id to which this comment panel belongs
     */
    public CommentPanel(Integer postId) {
	this.postId = postId;

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
	this.add(commentInputTextArea);

	Button commentButton = new Button("Post a comment", new ClickHandler() {
	    public void onClick(ClickEvent event) {
		commentInputTextArea.setVisible(true);
	    }
	});
	this.add(commentButton);
    }

}
