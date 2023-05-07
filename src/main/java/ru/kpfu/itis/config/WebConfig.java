package ru.kpfu.itis.config;

import io.pebbletemplates.pebble.PebbleEngine;
import io.pebbletemplates.pebble.loader.Loader;
import io.pebbletemplates.pebble.loader.Servlet5Loader;
import io.pebbletemplates.pebble.loader.ServletLoader;
import io.pebbletemplates.spring.servlet.PebbleViewResolver;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.handler.MappedInterceptor;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import ru.kpfu.itis.interceptor.ArticleInterceptor;

import jakarta.servlet.Filter;
import ru.kpfu.itis.pebble.CustomPebbleExtension;

@Configuration
@ComponentScan(basePackages = {"ru.kpfu.itis.controller", "ru.kpfu.itis.interceptor"})
@EnableWebMvc
@PropertySource("classpath:application.properties")
public class WebConfig extends WebMvcConfigurationSupport {

    @Resource
    private Environment env;

    @Autowired
    private ArticleInterceptor articleInterceptor;

    @Autowired
    private ServletContext servletContext;

    @Bean
    public ViewResolver viewResolver() {
        PebbleViewResolver resolver = new PebbleViewResolver(pebbleEngine());
        resolver.setPrefix(env.getRequiredProperty("pebble.prefix"));
        resolver.setSuffix(env.getRequiredProperty("pebble.suffix"));
        resolver.setRedirectContextRelative(false);
        return resolver;
    }

    @Bean
    public PebbleEngine pebbleEngine() {
        return new PebbleEngine.Builder()
                .loader(pebbleLoader())
                .extension(pebbleExtension()).build();
    }

    private Loader<?> pebbleLoader() {
        return new Servlet5Loader(servletContext);
    }

    @Bean
    public CustomPebbleExtension pebbleExtension() {
        return new CustomPebbleExtension();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("/WEB-INF/assets/css/");
        registry.addResourceHandler("/fonts/**").addResourceLocations("/WEB-INF/assets/fonts/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(articleInterceptor).addPathPatterns("/**");
    }

    @Override
    public RequestMappingHandlerMapping createRequestMappingHandlerMapping() {
        return new RequestMappingHandlerMapping();
    }
}
