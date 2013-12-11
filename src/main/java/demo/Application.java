package demo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.util.ClassUtils;

import com.sun.jersey.spi.container.servlet.ServletContainer;

@ComponentScan
@EnableAutoConfiguration
@Path("/hello")
public class Application {

	@Value("${message:World}")
	private String msg;

	@GET
	public String message() {
		return "Hello " + msg;
	}

	@Bean
	public ServletRegistrationBean jerseyServlet() {
		ServletRegistrationBean registration = new ServletRegistrationBean(
				new ServletContainer(), "/rest/*");
		registration.addInitParameter("com.sun.jersey.config.property.packages",
				ClassUtils.getPackageName(Application.class));
		return registration;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
