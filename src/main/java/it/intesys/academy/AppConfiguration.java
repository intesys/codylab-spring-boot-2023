package it.intesys.academy;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import it.intesys.academy.service.ProjectService;
import it.intesys.academy.service.PropertyMessageService;
import it.intesys.academy.service.SettingsService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
public class AppConfiguration {


    @Bean
    public Properties appProperties() {
        Properties prop = new Properties();
        try (InputStream input = AppConfiguration.class.getClassLoader().getResourceAsStream("application.properties")) {
            prop.load(input);
        } catch (IOException ex) {
            throw new IllegalStateException("Property load fail", ex);
        }

        return prop;
    }

    @Bean
    DataSource dataSource(@Qualifier("appProperties") Properties properties) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(properties.getProperty("database.url"));
        hikariConfig.setUsername(properties.getProperty("database.user"));
        hikariConfig.setPassword(properties.getProperty("database.password"));
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
    PropertyMessageService propertyMessageService(@Qualifier("appProperties") Properties properties) {
        return new PropertyMessageService(properties);
    }

}
