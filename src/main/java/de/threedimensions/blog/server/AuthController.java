package de.threedimensions.blog.server;

import java.util.List;
import java.util.UUID;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import de.threedimensions.blog.server.dao.UserDao;
import de.threedimensions.blog.server.model.User;
import de.threedimensions.blog.server.openid.MyHttpCacheProvider;
import de.threedimensions.blog.server.util.HttpCookies;
import de.threedimensions.blog.shared.FrontendConstants;

/**
 * Authentication with OpenId.
 * 
 * @author chris
 */
@Controller
@RequestMapping("/auth")
public class AuthController {

    private static final Logger LOG = Logger.getLogger(AuthController.class.getName());
    private static final String BLOG_BASE_URL = "http://threedimensionsblog.appspot.com";

    @Autowired
    private UserDao userDao;

    private ConsumerManager openIdConsumerManager;

    @Value("#{ systemProperties['localSimulation'] != null }")
    private boolean localSimulation = false;

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

    @RequestMapping(value = "/openIdLogin", method = RequestMethod.GET)
    public @ResponseBody
    String doOpenIdLogin(HttpServletRequest request, HttpServletResponse response) {

	HttpCookies.resetCookie(request, response, FrontendConstants.USER_ID_COOKIE_NAME);
	HttpCookies.resetCookie(request, response, FrontendConstants.OPEN_ID_IDENTIFIER_COOKIE_NAME);

	UUID uuidForUser = UUID.randomUUID();
	userDao.save(new User(uuidForUser));
	HttpCookies.setCookie(request, response, FrontendConstants.UUID_COOKIE_NAME, uuidForUser.toString(), true);

	if (localSimulation) {
	    setLoggedInCookies(request, response, new Identifier() {
		@Override
		public String getIdentifier() {
		    return "https://www.google.com/accounts/o8/id?id=AItOawlR6oC_UHENI63N7SOXmEkj2u0jQvXXXHH";
		}
	    }, "chris.wewerka@googlemail.com");
	    return new RedirectView("/Blog.html?gwt.codesvr=127.0.0.1:9997").getUrl();
	}

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
    public ModelAndView openIdAuthResponse(HttpServletRequest request, HttpServletResponse response) {

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

		setLoggedInCookies(request, response, verifiedId, email);

		LOG.fine("Redirecting to " + BLOG_BASE_URL);
		return new ModelAndView(new RedirectView(BLOG_BASE_URL));
	    } else {
		LOG.warning("Unable to extract email from open id auth");
		throw new RuntimeException("Unable to extract email from open id auth");
	    }

	} else {
	    throw new RuntimeException("open id auth NOT successful for identifier");
	}
    }

    private void setLoggedInCookies(HttpServletRequest request, HttpServletResponse response, Identifier verifiedId,
	    String email) {
	HttpCookies.setCookie(request, response, FrontendConstants.USER_ID_COOKIE_NAME, email, true);
	HttpCookies.setCookie(request, response, FrontendConstants.OPEN_ID_IDENTIFIER_COOKIE_NAME,
		verifiedId.getIdentifier(), true);

	LOG.fine("Cookie set : " + email + ", " + verifiedId.getIdentifier());
    }
}
