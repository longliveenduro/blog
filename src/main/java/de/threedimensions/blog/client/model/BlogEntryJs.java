package de.threedimensions.blog.client.model;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsDate;

/**
 * @author chris
 * 
 */
public final class BlogEntryJs extends JavaScriptObject {

    protected BlogEntryJs() {
    }

    public final native String getId() /*-{ return this.id; }-*/;

    public final native String getHeading() /*-{ return this.heading; }-*/;

    public final native String getContent() /*-{ return this.content; }-*/;

    public final native double getCreationTimeAsDouble() /*-{ return this.creationTime; }-*/;

    public final JsDate getCreationTime() {
	return JsDate.create(getCreationTimeAsDouble());
    }
}
