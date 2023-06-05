package it.intesys.academy;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import it.intesys.academy.service.ProjectService;
import it.intesys.academy.service.SettingsService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppConfiguration {

    public static Properties appProperties;

    public static Properties appProperties() {
        if (appProperties == null) {
            Properties prop = new Properties();
            try (InputStream input = AppConfiguration.class.getClassLoader().getResourceAsStream("application.properties")) {
                prop.load(input);
            } catch (IOException ex) {
                throw new IllegalStateException("Property load fail", ex);
            }
            appProperties = prop;
        }

        return appProperties;
    }

    public static DataSource dataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(appProperties().getProperty("db.url"));
        hikariConfig.setUsername(appProperties().getProperty("db.user"));
        hikariConfig.setPassword(appProperties().getProperty("db.password"));
        hikariConfig.setDriverClassName("org.h2.Driver");
        return new HikariDataSource(hikariConfig);
    }

    public static JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    public static NamedParameterJdbcTemplate namedJdbcTemplate() {
        return new NamedParameterJdbcTemplate(dataSource());
    }

    public static ProjectService projectService() {
        return new ProjectService(namedJdbcTemplate(), settingsService());
    }

    public static SettingsService settingsService() {
        return new SettingsService(dataSource());
    }
}
