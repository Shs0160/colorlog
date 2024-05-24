package color_log.colorlog;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")// 모든 origin 허용, 필요에 따라 특정 origin만 허용할 수도 있음
                .allowedOrigins("http://colorlog.site", "https://colorlog.site")
                .allowedMethods("GET", "POST", "PUT", "DELETE") // 허용할 HTTP 메서드 지정
                .allowedHeaders("*"); // 모든 헤더 허용

    }
    /*@Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/user/{userId}").setViewName("forward:/index.html");
    }*/


}
