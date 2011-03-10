package de.threedimensions.blog.client.components;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;

import de.threedimensions.blog.client.event.EventHandler;
import de.threedimensions.blog.client.event.OpenIdLoginUrlReceivedEvent;
import de.threedimensions.blog.client.rest.BlogRestClient;
import de.threedimensions.blog.shared.FrontendConstants;

/**
 * @author chris
 * 
 */
public class Navbar extends Composite implements EventHandler<OpenIdLoginUrlReceivedEvent> {
    interface NavbarUiBinder extends UiBinder<Widget, Navbar> {
    }

    private static NavbarUiBinder uiBinder = GWT.create(NavbarUiBinder.class);
    private final BlogRestClient blogRestClient;

    @UiField
    InlineLabel userName;

    @UiField
    Anchor loginLink;

    public Navbar(BlogRestClient blogRestClient) {
	this.blogRestClient = blogRestClient;
	initWidget(uiBinder.createAndBindUi(this));

	final String openIdIdentifier = Cookies.getCookie(FrontendConstants.OPEN_ID_IDENTIFIER_COOKIE_NAME);
	if (openIdIdentifier == null) {
	    loginLink.setVisible(true);
	    userName.setText("Not logged in");
	} else {
	    loginLink.setVisible(false);
	    userName.setText(Cookies.getCookie(FrontendConstants.USER_ID_COOKIE_NAME));
	}
    }

    @UiHandler("loginLink")
    void buttonClick(ClickEvent event) {
	blogRestClient.prepareOpenIdLogin(this);
    }

    public native void redirectToOpenIdLogin(String url) /*-{ $wnd.location = url; }-*/;

    @Override
    public void handleEvent(OpenIdLoginUrlReceivedEvent event) {
	redirectToOpenIdLogin(event.getContent());

    }
}
