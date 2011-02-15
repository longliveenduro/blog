package de.threedimensions.blog.client.model;

import java.util.Date;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * @author chris
 * 
 */
public class BlogEntryJs extends JavaScriptObject {

    protected BlogEntryJs() {
    }

    public final native int getId() /*-{ return this.id; }-*/;

    public final native String getHeading() /*-{ return this.heading; }-*/;

    public final native String getContent() /*-{ return this.content; }-*/;

    public final native Date getCreationTime() /*-{ return this.creationTime; }-*/;
}
