package pl.playerony.cantor.config.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CORSFilter implements Filter {

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		HttpServletResponse httpServletResponse = (HttpServletResponse) arg1;
		httpServletResponse.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
		httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
		httpServletResponse.setHeader("Access-Control-Allow-Headers", "X-Auth-Token, Content-Type");
		httpServletResponse.setHeader("Access-Control-Expose-Headers", "custom-header1, custom-header2");
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "false");
        httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
        arg2.doFilter(arg0, arg1);
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
