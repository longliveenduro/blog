package de.threedimensions.blog.client.components;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author chris
 * 
 */
public class BlogEntryComponent extends Composite {

    private static BlogEntryComponentUiBinder uiBinder = GWT.create(BlogEntryComponentUiBinder.class);

    interface BlogEntryComponentUiBinder extends UiBinder<Widget, BlogEntryComponent> {
    }

    public BlogEntryComponent() {
	initWidget(uiBinder.createAndBindUi(this));
    }

}
