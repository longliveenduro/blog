package de.threedimensions.blog.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openid4java.OpenIDException;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.discovery.Discovery;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.discovery.html.HtmlResolver;
import org.openid4java.discovery.xri.XriDotNetProxyResolver;
import org.openid4java.discovery.yadis.YadisResolver;
import org.openid4java.message.AuthRequest;
import org.openid4java.message.ax.FetchRequest;
import org.openid4java.server.RealmVerifierFactory;
import org.openid4java.util.HttpFetcherFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import de.threedimensions.blog.server.model.BlogEntry;
import de.threedimensions.blog.server.model.BlogEntryRef;
import de.threedimensions.blog.server.openid.MyHttpCacheProvider;

@Controller
@RequestMapping("/*")
public class BlogController
{
	private ConsumerManager openIdConsumerManager;

	public BlogController()
	{
		HttpFetcherFactory httpFetcherFactory = new HttpFetcherFactory(new MyHttpCacheProvider());
		HtmlResolver HtmlResolver = new HtmlResolver(httpFetcherFactory);
		YadisResolver resolver = new YadisResolver(httpFetcherFactory);
		XriDotNetProxyResolver xriDotNetProxyResolver = new XriDotNetProxyResolver(httpFetcherFactory);
		RealmVerifierFactory realmVerifierFactory = new RealmVerifierFactory(resolver);

		Discovery discovery = new Discovery(HtmlResolver, resolver, xriDotNetProxyResolver);

		// instantiate a ConsumerManager object
		openIdConsumerManager = new ConsumerManager(realmVerifierFactory, discovery, httpFetcherFactory);
	}

	@RequestMapping(value = "/posts", method = RequestMethod.GET)
	public @ResponseBody
	List<BlogEntryRef> getBlogEntries()
	{
		List<BlogEntryRef> blogEntryRefs = new ArrayList<BlogEntryRef>();
		blogEntryRefs.add(new BlogEntryRef(1l, "posts/1"));
		blogEntryRefs.add(new BlogEntryRef(2l, "posts/2"));
		return blogEntryRefs;
	}

	@RequestMapping(value = "/posts/{postId}", method = RequestMethod.GET)
	public @ResponseBody
	BlogEntry getBlogEntry(@PathVariable Long postId)
	{
		BlogEntry blogEntry = new BlogEntry("Blog Entry from Server with id " + postId, "Blog content from server",
				new Date());
		blogEntry.setId(postId);
		return blogEntry;
	}

	@RequestMapping(value = "/auth/{userSuppliedString}", method = RequestMethod.POST)
	public String doOpenIdLogin(@PathVariable String userSuppliedString)
	{
		try {
			// configure the return_to URL where your application will receive
			// the authentication responses from the OpenID provider
			String returnToUrl = "http://threedimensionsblog.appspot.com/app/auth";

			// perform discovery on the user-supplied identifier
			List discoveries = openIdConsumerManager.discover(userSuppliedString);

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

			// Option 1: GET HTTP-redirect to the OpenID Provider endpoint
			// The only method supported in OpenID 1.x
			// redirect-URL usually limited ~2048 bytes
			return (authReq.getDestinationUrl(true));

		} catch (OpenIDException e) {
			// present error to the user
			throw new RuntimeException(e);
		}
	}
}
