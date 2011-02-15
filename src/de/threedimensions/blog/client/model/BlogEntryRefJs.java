package de.threedimensions.blog.client.model;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * @author chris
 * 
 */
public class BlogEntryRefJs extends JavaScriptObject {

    protected BlogEntryRefJs() {
    }

    public final native Long getId() /*-{ return this.id; }-*/;

    public final native String getUrl() /*-{ return this.url; }-*/;
}
