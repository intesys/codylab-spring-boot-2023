package it.intesys.academy.service;

import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.dto.ProjectDTO;
import it.intesys.academy.repository.ProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class ProjectService {

    private static final Logger log = LoggerFactory.getLogger(ProjectService.class);

    private final ProjectRepository projectRepository;
    private final SettingsService settingsService;

    public ProjectService(ProjectRepository projectRepository, SettingsService settingsService) {

        this.projectRepository = projectRepository;
        this.settingsService = settingsService;
    }

    public List<ProjectDTO> readProjects(String username) {

        List<Integer> userProjectIds = settingsService.getUserProjects(username);

        List<ProjectDTO> userProjects = projectRepository.readProjects(userProjectIds);

        HashMap<Integer, ProjectDTO> mapProjects = new HashMap<>();

        for (ProjectDTO p: userProjects) {
            mapProjects.put(p.getId(), p);
        }

        List<Integer> projectIds = userProjects.stream()
                                           .map(ProjectDTO::getId)
                                           .toList();

        List<IssueDTO> issues = projectRepository.readIssues(projectIds);

        for (IssueDTO issue : issues) {
            mapProjects.get(issue.getProjectId()).addIssue(issue);
        }

        return userProjects;

    }

}
