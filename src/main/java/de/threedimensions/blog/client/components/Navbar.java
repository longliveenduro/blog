package de.threedimensions.blog.client.components;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;

import de.threedimensions.blog.client.rest.BlogRestClient;

/**
 * @author chris
 * 
 */
public class Navbar extends Composite {
    interface NavbarUiBinder extends UiBinder<Widget, Navbar> {
    }

    private static NavbarUiBinder uiBinder = GWT.create(NavbarUiBinder.class);

    private BlogRestClient blogRestClient = new BlogRestClient();

    @UiField
    InlineLabel userName;

    @UiField
    Anchor loginLink;

    public Navbar() {
	initWidget(uiBinder.createAndBindUi(this));
	userName.setText("test@spam.la");
    }

    @UiHandler("loginLink")
    void buttonClick(ClickEvent event) {
	userName.setText("login clicked");
	// blogRestClient.prepareOpenIdLogin(this);
    }

    // final String openIdIdentifier =
    // Cookies.getCookie(FrontendConstants.OPEN_ID_IDENTIFIER_COOKIE_NAME);
    // if (openIdIdentifier == null) {
    // Button loginButton = new Button("Login with Google Account", new
    // ClickHandler() {
    // public void onClick(ClickEvent event) {
    // blogRestClient.prepareOpenIdLogin(Blog.this);
    // }
    // });
    // contentMiddlePanel.add(loginButton);
    // } else {
    // Label userNameLabel = new Label("Logged in with "
    // + Cookies.getCookie(FrontendConstants.USER_ID_COOKIE_NAME));
    // contentMiddlePanel.add(userNameLabel);
    // }

}
