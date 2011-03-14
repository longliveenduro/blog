package de.threedimensions.blog.client.components;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import de.threedimensions.blog.client.event.BlogEntryReceivedEvent;
import de.threedimensions.blog.client.event.EventHandler;
import de.threedimensions.blog.client.model.BlogEntryJs;

/**
 * @author chris
 * 
 */
public class BlogEntryComponent extends Composite implements EventHandler<BlogEntryReceivedEvent> {
    interface BlogEntryComponentUiBinder extends UiBinder<Widget, BlogEntryComponent> {
    }

    private static BlogEntryComponentUiBinder uiBinder = GWT.create(BlogEntryComponentUiBinder.class);

    @UiField
    Label blogPostDate;

    @UiField
    InlineLabel blogPostTime;

    @UiField
    Label blogPostTitle;

    @UiField
    Label blogPostContent;

    private DateTimeFormat dateFormat = DateTimeFormat.getFormat(PredefinedFormat.DATE_FULL);

    public BlogEntryComponent() {
	initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void handleEvent(BlogEntryReceivedEvent event) {
	BlogEntryJs blogEntryJs = event.getContent();
	blogPostDate.setText(blogEntryJs.getCreationTime().toLocaleDateString());
	blogPostTitle.setText(blogEntryJs.getHeading());
	blogPostContent.setText(blogEntryJs.getContent());
	blogPostTime.setText(blogEntryJs.getCreationTime().toLocaleTimeString());
    }

}
