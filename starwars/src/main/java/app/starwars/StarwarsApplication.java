package app.starwars;

import com.sun.faces.config.ConfigureListener;
import javax.faces.webapp.FacesServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class StarwarsApplication {

  public static void main(String[] args) {
    SpringApplication.run(StarwarsApplication.class, args);
  }

  @Bean
  public ServletRegistrationBean facesServletRegistrationBean() {
    ServletRegistrationBean registration = new ServletRegistrationBean(new FacesServlet(),
        "/faces/*");
    registration.setLoadOnStartup(1);
    registration.addUrlMappings("/faces/*");
    return registration;
  }

  @Bean
  public ServletContextInitializer servletContextInitializer() {
    return servletContext -> {
      servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration",
          Boolean.TRUE.toString());
      servletContext.setInitParameter("primefaces.THEME", "sunny");
    };
  }

  @Bean
  public ServletListenerRegistrationBean<ConfigureListener> jsfConfigureListenerRegistrationBean() {
    return new ServletListenerRegistrationBean<>(new ConfigureListener());
  }

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
