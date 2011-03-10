package de.threedimensions.blog.client.components;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import de.threedimensions.blog.client.model.BlogEntryJs;

/**
 * @author chris
 * 
 */
public class BlogEntryComponent extends Composite {
    interface BlogEntryComponentUiBinder extends UiBinder<Widget, BlogEntryComponent> {
    }

    private static BlogEntryComponentUiBinder uiBinder = GWT.create(BlogEntryComponentUiBinder.class);

    @UiField
    Label blogPostDate;

    private DateTimeFormat dateFormat = DateTimeFormat.getFormat(PredefinedFormat.DATE_FULL);

    public BlogEntryComponent(BlogEntryJs blogEntryJs) {
	initWidget(uiBinder.createAndBindUi(this));

	blogPostDate.setText(blogEntryJs.getCreationTime().toLocaleDateString());
    }

}
