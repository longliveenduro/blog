package de.threedimensions.blog.shared;

/**
 * @author chris
 */
public interface FrontendConstants {
    /** key for the cookie containing the open id identifier */
    public static final String OPEN_ID_IDENTIFIER_COOKIE_NAME = "openIdCookieIdentifier";

    /**
     * key for the cookie containing the user email associated with the open id
     * account
     */
    public static final String USER_ID_COOKIE_NAME = "userIdCookieName";

    /**
     * UUID cookie name for the UUID which is generated right before the
     * redirect to the openid provider. Used to match the user logged in with
     * the authenticated user.
     */
    public static final String UUID_COOKIE_NAME = "uuid";
}
