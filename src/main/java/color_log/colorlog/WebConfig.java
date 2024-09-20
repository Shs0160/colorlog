package color_log.colorlog;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://colorlog.site", "https://colorlog.site", "http://localhost:3000", "http://192.168.1.8:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*");

    }
    /*@Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/user/{userId}").setViewName("forward:/index.html");
    }*/


}
