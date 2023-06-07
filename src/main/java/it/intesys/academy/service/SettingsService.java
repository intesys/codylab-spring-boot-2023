package it.intesys.academy.service;

import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

@Service
public class SettingsService {

    private final DataSource dataSource;

    public SettingsService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Integer> getUserProjects(String username) {
        return List.of(1, 2, 3);
    }
}
