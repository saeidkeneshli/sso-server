package io.github.saeidkeneshli.server.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@Configuration
@EnableSpringDataWebSupport
public class ApplicationConfig {
    @Configuration
    public class LoggerConfig {

        @Bean
        public Logger customizedLogger() {
            return LoggerFactory.getLogger("custom-logger");
        }
    }
}
