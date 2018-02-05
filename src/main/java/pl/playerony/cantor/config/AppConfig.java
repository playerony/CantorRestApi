package pl.playerony.cantor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import pl.playerony.cantor.utils.TokenHelper;

@Configuration
public class AppConfig {
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public TokenHelper tokenHelper() {
		return new TokenHelper();
	}
	
}
