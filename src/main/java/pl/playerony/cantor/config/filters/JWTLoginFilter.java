package pl.playerony.cantor.config.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import pl.playerony.cantor.database.models.Role;
import pl.playerony.cantor.database.models.User;
import pl.playerony.cantor.database.services.RoleService;
import pl.playerony.cantor.database.services.UserService;
import pl.playerony.cantor.exceptions.CantorRestApiException;
import pl.playerony.cantor.utils.TokenHelper;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {
	@Autowired
	private TokenHelper tokenHelper;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	public JWTLoginFilter(String url, AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authenticationManager);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
    	tokenHelper.addAuthentication(response, authResult.getName());
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
        List<Role> roles = new ArrayList<>();

        try {
        	User foundUser = userService.fetchUserByUsername(user.getUsername());
			Role role = roleService.fetchRoleByRoleId(foundUser.getRoleId());
			roles.add(role);
        } catch (CantorRestApiException e) {
            e.printStackTrace();
        }

        return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), roles));
    }

}
