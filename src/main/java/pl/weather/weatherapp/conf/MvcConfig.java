package pl.weather.weatherapp.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pl.weather.weatherapp.interceptor.LoggingInterceptor;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    private final JdbcTemplate jdbcTemplate;

    public MvcConfig(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoggingInterceptor(jdbcTemplate));
    }
}
