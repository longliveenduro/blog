package de.threedimensions.blog.server.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author http://chaoticjava.com/posts/using-openid-within-gwt/
 */
public final class HttpCookies {
    public static final int dayInSeconds = 86400;

    public static Cookie findCookie(HttpServletRequest request, String name) {
	final Cookie[] cookies = request.getCookies();

	if (cookies == null) {
	    return null;
	}

	for (Cookie cookie : cookies) {
	    if (name.equals(cookie.getName())) {
		return cookie;
	    }
	}

	return null;
    }

    public static String getCookieValue(HttpServletRequest request, String name) {
	Cookie cookie = findCookie(request, name);

	return cookie != null ? cookie.getValue() : null;
    }

    public static void resetCookie(HttpServletRequest request, HttpServletResponse response, String name) {
	Cookie cookie = findCookie(request, name);
	if (cookie != null) {
	    cookie.setMaxAge(0);
	    response.addCookie(cookie);
	}
    }

    public static void setCookie(HttpServletRequest request, HttpServletResponse response, final String cookieName,
	    String cookieValue) {
	Cookie cookie = findCookie(request, cookieName);

	if (cookie == null) {
	    cookie = new Cookie(cookieName, cookieValue);
	} else {
	    cookie.setValue(cookieValue);
	}

	cookie.setMaxAge(dayInSeconds); // one day
	response.addCookie(cookie);
    }
}
