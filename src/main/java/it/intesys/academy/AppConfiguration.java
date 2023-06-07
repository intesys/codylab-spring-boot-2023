package it.intesys.academy;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import it.intesys.academy.service.ProjectService;
import it.intesys.academy.service.PropertyMessageService;
import it.intesys.academy.service.SettingsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:application.properties")
public class AppConfiguration {


    @Bean
    DataSource dataSource(Environment environment) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(environment.getProperty("database.url"));
        hikariConfig.setUsername(environment.getProperty("database.user"));
        hikariConfig.setPassword(environment.getProperty("database.password"));
        hikariConfig.setDriverClassName("org.h2.Driver");
        return new HikariDataSource(hikariConfig);
    }

    @Bean
    NamedParameterJdbcTemplate namedJdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    public ProjectService projectService(NamedParameterJdbcTemplate namedParameterJdbcTemplate, SettingsService settingsService) {
        return new ProjectService(namedParameterJdbcTemplate, settingsService);
    }

    @Bean
    public SettingsService settingsService(DataSource dataSource) {
        return new SettingsService(dataSource);
    }

    @Bean
    PropertyMessageService propertyMessageService(Environment environment) {
        return new PropertyMessageService(environment.getProperty("app.message"));
    }

}
