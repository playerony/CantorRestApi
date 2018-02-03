package pl.playerony.cantor.config;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import io.swagger.jaxrs.config.BeanConfig;
import pl.playerony.cantor.controllers.UserController;

@Component
@ApplicationPath("/cantor")
public class JerseyConfig extends ResourceConfig {
	public JerseyConfig()
    {
		registerEndpoints();
		configureSwagger();
    }
	
	private void configureSwagger() {
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0.2");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setHost("localhost:8080");
        beanConfig.setBasePath("/");
        beanConfig.setResourcePackage("pl.playerony.cantor");
        beanConfig.setPrettyPrint(true);
        beanConfig.setScan(true);
    }
    
    private void registerEndpoints() {
    	register(UserController.class);
    }
}
