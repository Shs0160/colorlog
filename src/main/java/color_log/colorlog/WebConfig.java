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
                .allowedOrigins("http://colorlog.site", "https://colorlog.site", "http://localhost:3000",
                        "http://172.17.7.153:3000", "http://172.17.7.168", "colorlog.site", "www.colorlog.site",
                        "http://www.colorlog.site")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*");

    }
    /*@Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/user/{userId}").setViewName("forward:/index.html");
    }*/


}
