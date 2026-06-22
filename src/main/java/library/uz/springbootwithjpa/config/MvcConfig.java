package library.uz.springbootwithjpa.config;

import library.uz.springbootwithjpa.filter.AuthFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations(
                        "C:/Users/Abdulloh/Desktop/spring-boot-with-jpa/images/");
    }

//    @Bean
//    public FilterRegistrationBean<AuthFilter> authFilter() {
//        FilterRegistrationBean<AuthFilter> registration = new FilterRegistrationBean<>();
//
//        registration.setFilter(new AuthFilter());
//        registration.addUrlPatterns("/*"); // hamma requestlar
//        registration.setOrder(1); // priority
//
//        return registration;
//    }
}
