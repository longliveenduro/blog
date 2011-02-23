package de.threedimensions.blog.server.openid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Implementation of {@link UserDetailsService} for OpenId
 */
public class OpenIdUserDetailsServiceImpl implements UserDetailsService
{

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		return new User(username, "", true, true, true, true, getAuthorities());
	}

	/**
	 * Utility method for creating a list of {@link GrantedAuthority} objects
	 * 
	 * @return
	 */
	private Collection<GrantedAuthority> getAuthorities()
	{
		List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>(2);
		authList.add(new GrantedAuthorityImpl("ROLE_USER"));

		return authList;
	}
}