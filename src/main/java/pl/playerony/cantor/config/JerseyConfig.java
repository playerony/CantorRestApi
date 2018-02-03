package pl.playerony.cantor.config;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import pl.playerony.cantor.controllers.UserController;

@Component
@ApplicationPath("/cantor")
public class JerseyConfig extends ResourceConfig {
	public JerseyConfig()
    {
		register(UserController.class);
    }
}
