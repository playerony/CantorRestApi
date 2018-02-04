package pl.playerony.cantor.config.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import pl.playerony.cantor.config.filters.JWTAuthenticationFilter;
import pl.playerony.cantor.config.filters.JWTLoginFilter;
import pl.playerony.cantor.exceptions.CantorRestApiException;

@Configuration
//@EnableGlobalMethodSecurity//(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Value("${query.user-query}")
    private String userQuery;

    @Value("${query.role-query}")
    private String roleQuery;
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws CantorRestApiException {
        try {
			auth.jdbcAuthentication()
			    .usersByUsernameQuery(userQuery)
			    .authoritiesByUsernameQuery(roleQuery)
			    .passwordEncoder(encoder)
			    .dataSource(dataSource);
		} catch (Exception e) {
			throw new CantorRestApiException("Some problems by setting security configuration.", e);
		}
    }

    @Override
    protected void configure(HttpSecurity http) throws CantorRestApiException {
        try {
			http.csrf().disable();
			//http.cors().disable();
			http.authorizeRequests()
	            .anyRequest()
	            .permitAll();
				//.and()
                //.addFilterBefore(new JWTLoginFilter("/api/getToken", authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                //.addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		} catch (Exception e) {
			throw new CantorRestApiException("Some problems by setting security configuration.", e);
		}

    }
}
