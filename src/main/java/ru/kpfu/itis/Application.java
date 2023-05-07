package ru.kpfu.itis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.kpfu.itis.config.RootConfig;
import ru.kpfu.itis.config.SecurityConfig;
import ru.kpfu.itis.config.WebConfig;

@Configuration
@EnableAutoConfiguration
@Import({RootConfig.class, SecurityConfig.class, WebConfig.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.run(args);
    }

}
