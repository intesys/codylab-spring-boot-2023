package it.intesys.academy.service;

import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.dto.ProjectDTO;
import it.intesys.academy.entity.Project;
import it.intesys.academy.mapper.ProjectMapper;
import it.intesys.academy.repository.IssueRepository;
import it.intesys.academy.repository.ProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ProjectService {

    private static final Logger log = LoggerFactory.getLogger(ProjectService.class);

    private final ProjectRepository projectRepository;

    private final IssueRepository issueRepository;
    private final SettingsService settingsService;

    private final UserProjectService userProjectService;

    private final ProjectMapper projectMapper;

    public ProjectService(ProjectRepository projectRepository, IssueRepository issueRepository, SettingsService settingsService,
        UserProjectService userProjectService, ProjectMapper projectMapper) {

        this.projectRepository = projectRepository;
        this.issueRepository = issueRepository;
        this.settingsService = settingsService;
        this.userProjectService = userProjectService;
        this.projectMapper = projectMapper;
    }

    public ProjectDTO readProjectWithIssue(int projectId, String username) {

        if (userProjectService.canThisUserReadThisProject(username, projectId))

            return projectMapper.toDTO(projectRepository.readProject(projectId));

        throw new RuntimeException("Security constraints violation");

    }

    public List<ProjectDTO> readProjectsWithIssues(String username) {

        return readProjectsWithIssues(settingsService.getUserProjects(username));

    }

    private List<ProjectDTO> readProjectsWithIssues(List<Integer> userProjectIds) {
        /**
        List<ProjectDTO> userProjects = projectRepository.readProjects(userProjectIds);

        HashMap<Integer, ProjectDTO> mapProjects = new HashMap<>();

        for (ProjectDTO p: userProjects) {
            mapProjects.put(p.getId(), p);
        }
        for(Integer i:userProjectIds){
            log.info(String.valueOf(i));
        }

        List<Integer> projectIds = userProjects.stream()
                                           .map(ProjectDTO::getId)
                                           .toList();

        List<IssueDTO> issues = issueRepository.readIssues(projectIds);

        for (IssueDTO issue : issues) {
            mapProjects.get(issue.getProjectId()).addIssue(issue);
        }

        return userProjects;
         */
        List<Project> userProjectEntity = projectRepository.readProjects(userProjectIds);
        List<ProjectDTO> userProjectDTO = userProjectEntity.stream()
                                            .map(projectMapper::toDTO)
                                            .toList();
        return userProjectDTO;
    }

    public ProjectDTO createProject(ProjectDTO projectDTO, String username){
        Project project = projectMapper.toEntity(projectDTO);
        project = projectRepository.createProject(project);
        userProjectService.createUserProject(project.getId(),username);
        return projectMapper.toDTO(project);
    }

    public ProjectDTO updateProject(ProjectDTO projectDTO, String username, Integer projectId){
        Project project = projectMapper.toEntity(projectDTO);
        if(!userProjectService.canThisUserReadThisProject(username,project.getId())){
            throw new RuntimeException("errore");
        }
        return projectMapper.toDTO(projectRepository.updateRepository(project));
    }

    public void deleteProject(Integer projectId, String username){
        if(!userProjectService.canThisUserReadThisProject(username,projectId)){
            throw new RuntimeException("errore");
        }
        projectRepository.deleteProject(projectId);
    }
}
