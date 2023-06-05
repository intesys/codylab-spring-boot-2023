package it.intesys.academy.service;

import javax.sql.DataSource;
import java.util.List;

public class SettingsService {

    private final DataSource dataSource;

    public SettingsService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Integer> getUserProjects(String username) {
        return List.of(1, 2, 3);
    }
}
