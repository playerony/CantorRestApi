package pl.playerony.cantor.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class DatabaseConfig {
	@Autowired
	private Environment enviroment;

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(enviroment.getProperty("db.driver"));
		dataSource.setUrl(enviroment.getProperty("db.url"));
		dataSource.setUsername(enviroment.getProperty("db.username"));
		dataSource.setPassword(enviroment.getProperty("db.password"));

		return dataSource;
	}

}
