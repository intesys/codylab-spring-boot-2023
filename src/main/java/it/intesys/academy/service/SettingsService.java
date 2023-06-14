package it.intesys.academy.service;

import it.intesys.academy.dto.ProjectDTO;
import it.intesys.academy.dto.UserProjectDTO;
import it.intesys.academy.repository.UserProjectRepository;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

@Service
public class SettingsService {

    private final DataSource dataSource;
    private final UserProjectRepository userProjectRepository;

    public SettingsService(DataSource dataSource, UserProjectRepository userProjectRepository) {
        this.dataSource = dataSource;
        this.userProjectRepository = userProjectRepository;
    }

    public List<Integer> getUserProjects(String username) {
        List<UserProjectDTO> projects = userProjectRepository.getUserProjects(username);
        List<Integer> projectsIds = projects.stream()
                .map(UserProjectDTO::getId)
                .toList();
        return projectsIds;
    }
}
