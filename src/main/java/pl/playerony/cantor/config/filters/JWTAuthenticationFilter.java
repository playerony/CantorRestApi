package pl.playerony.cantor.config.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import pl.playerony.cantor.utils.TokenHelper;

public class JWTAuthenticationFilter extends GenericFilterBean {
	private TokenHelper tokenHelper;
	
	public JWTAuthenticationFilter(TokenHelper tokenHelper) {
		this.tokenHelper = tokenHelper;
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		Authentication authentication = tokenHelper.getAuthentication((HttpServletRequest) request);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(request, response);
	}

}
