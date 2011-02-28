package de.threedimensions.blog.server;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openid4java.OpenIDException;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.consumer.VerificationResult;
import org.openid4java.discovery.Discovery;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.discovery.Identifier;
import org.openid4java.discovery.html.HtmlResolver;
import org.openid4java.discovery.xri.XriDotNetProxyResolver;
import org.openid4java.discovery.yadis.YadisResolver;
import org.openid4java.message.AuthRequest;
import org.openid4java.message.AuthSuccess;
import org.openid4java.message.MessageException;
import org.openid4java.message.ParameterList;
import org.openid4java.message.ax.AxMessage;
import org.openid4java.message.ax.FetchRequest;
import org.openid4java.message.ax.FetchResponse;
import org.openid4java.server.RealmVerifierFactory;
import org.openid4java.util.HttpFetcherFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import de.threedimensions.blog.server.openid.MyHttpCacheProvider;
import de.threedimensions.blog.server.util.HttpCookies;
import de.threedimensions.blog.shared.FrontendConstants;

/**
 * @author chris
 * 
 */
@Controller
@RequestMapping("/auth")
public class AuthController {

    private static final Logger LOG = Logger.getLogger(AuthController.class.getName());

    private static final String BLOG_BASE_URL = "http://threedimensionsblog.appspot.com";

    private ConsumerManager openIdConsumerManager;

    public AuthController() {
	HttpFetcherFactory httpFetcherFactory = new HttpFetcherFactory(new MyHttpCacheProvider());
	HtmlResolver HtmlResolver = new HtmlResolver(httpFetcherFactory);
	YadisResolver resolver = new YadisResolver(httpFetcherFactory);
	XriDotNetProxyResolver xriDotNetProxyResolver = new XriDotNetProxyResolver(httpFetcherFactory);
	RealmVerifierFactory realmVerifierFactory = new RealmVerifierFactory(resolver);

	Discovery discovery = new Discovery(HtmlResolver, resolver, xriDotNetProxyResolver);

	// instantiate a ConsumerManager object
	openIdConsumerManager = new ConsumerManager(realmVerifierFactory, discovery, httpFetcherFactory);
    }

    @RequestMapping(value = "/{userSuppliedString}", method = RequestMethod.GET)
    public @ResponseBody
    String doOpenIdLogin(@PathVariable String userSuppliedString, HttpServletRequest request,
	    HttpServletResponse response) {

	HttpCookies.resetCookie(request, response, FrontendConstants.USER_ID_COOKIE_NAME);
	HttpCookies.resetCookie(request, response, FrontendConstants.OPEN_ID_IDENTIFIER_COOKIE_NAME);

	try {
	    // configure the return_to URL where your application will receive
	    // the authentication responses from the OpenID provider
	    String returnToUrl = BLOG_BASE_URL + "/app/auth/response";

	    // perform discovery on the user-supplied identifier
	    List discoveries = openIdConsumerManager.discover("https://www.google.com/accounts/o8/id");

	    // attempt to associate with the OpenID provider
	    // and retrieve one service endpoint for authentication
	    DiscoveryInformation discovered = openIdConsumerManager.associate(discoveries);

	    // store the discovery information in the user's session
	    // httpReq.getSession().setAttribute("openid-disc", discovered);

	    // obtain a AuthRequest message to be sent to the OpenID provider
	    AuthRequest authReq = openIdConsumerManager.authenticate(discovered, returnToUrl);

	    // Attribute Exchange example: fetching the 'email' attribute
	    FetchRequest fetch = FetchRequest.createFetchRequest();
	    fetch.addAttribute("email",
	    // attribute alias
		    "http://schema.openid.net/contact/email", // type URI
		    true); // required

	    // attach the extension to the authentication request
	    authReq.addExtension(fetch);

	    return authReq.getDestinationUrl(true);

	} catch (OpenIDException e) {
	    // present error to the user
	    throw new RuntimeException(e);
	}
    }

    @RequestMapping(value = "/response", method = RequestMethod.GET)
    public void openIdAuthResponse(HttpServletRequest request, HttpServletResponse response) {

	// extract the parameters from the authentication response
	// (which comes in as a HTTP request from the OpenID provider)
	ParameterList openidResp = new ParameterList(request.getParameterMap());

	// retrieve the previously stored discovery information
	// DiscoveryInformation discovered = (DiscoveryInformation)
	// session.getAttribute("discovered");

	// extract the receiving URL from the HTTP request
	StringBuffer receivingURL = request.getRequestURL();
	String queryString = request.getQueryString();
	if (queryString != null && queryString.length() > 0)
	    receivingURL.append("?").append(request.getQueryString());

	// verify the response
	VerificationResult verification = null;
	try {
	    verification = openIdConsumerManager.verify(receivingURL.toString(), openidResp, null);
	} catch (OpenIDException e) {
	    throw new RuntimeException("Error verifiying response from open id provider", e);
	}

	// examine the verification result and extract the verified identifier
	Identifier verifiedId = verification.getVerifiedId();

	if (verifiedId != null) {
	    // success, use the verified identifier to identify the user

	    LOG.fine("open id auth successful for identifier: " + verifiedId.getIdentifier());

	    AuthSuccess authSuccess = (AuthSuccess) verification.getAuthResponse();

	    if (authSuccess.hasExtension(AxMessage.OPENID_NS_AX)) {
		FetchResponse fetchResp;
		try {
		    fetchResp = (FetchResponse) authSuccess.getExtension(AxMessage.OPENID_NS_AX);
		} catch (MessageException e) {
		    throw new RuntimeException("Error fetching FetchResponse from open id auth response", e);
		}

		List emails = fetchResp.getAttributeValues("email");
		String email = (String) emails.get(0);
		LOG.fine("Email found in openid response: " + email);

		HttpCookies.setCookie(request, response, FrontendConstants.USER_ID_COOKIE_NAME, email);
		HttpCookies.setCookie(request, response, FrontendConstants.OPEN_ID_IDENTIFIER_COOKIE_NAME,
			verifiedId.getIdentifier());

		redirect(response);
	    } else {
		LOG.warning("Unable to extract email from open id auth");
	    }

	} else {
	    // OpenID authentication failed
	    LOG.warning("open id auth NOT successful for identifier: ");
	}
    }

    private void redirect(HttpServletResponse response) {
	try {
	    response.sendRedirect(BLOG_BASE_URL);
	} catch (IOException e) {
	    throw new RuntimeException("Unable to issue redirect", e);
	}
    }
}
