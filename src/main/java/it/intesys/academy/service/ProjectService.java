package it.intesys.academy.service;

import it.intesys.academy.domain.Project;
import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.dto.ProjectDTO;
import it.intesys.academy.domain.Issue;
import it.intesys.academy.domain.Project;
import it.intesys.academy.mapper.IssueMapper;
import it.intesys.academy.mapper.ProjectMapper;
import it.intesys.academy.repository.IssueRepository;
import it.intesys.academy.repository.ProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ProjectService {

    private static final Logger log = LoggerFactory.getLogger(ProjectService.class);

    private final ProjectRepository projectRepository;

    private final IssueRepository issueRepository;

    private final UserProjectService userProjectService;

    private final ProjectMapper projectMapper;

    private final IssueMapper issueMapper;

    public ProjectService(ProjectRepository projectRepository, IssueRepository issueRepository,
                          UserProjectService userProjectService, ProjectMapper projectMapper, IssueMapper issueMapper) {

        this.projectRepository = projectRepository;
        this.issueRepository = issueRepository;
        this.userProjectService = userProjectService;
        this.projectMapper = projectMapper;
        this.issueMapper = issueMapper;
    }

    public ProjectDTO readProjectWithIssue(int projectId, String username) {

        log.info("Reading project {} with issues, user {}", projectId, username);

        if (userProjectService.canThisUserReadThisProject(username, projectId)) {
            ProjectDTO projectDTO = projectMapper.toDto(projectRepository.readProject(projectId));
            issueRepository.readIssues(List.of(projectId))
                    .forEach(issue -> projectDTO.addIssue(issueMapper.toDto(issue)));
            return projectDTO;
        }

        throw new RuntimeException("Security constraints violation");

    }

    public ProjectDTO readProject(int projectId, String username) {

        log.info("Reading project {} , user {}", projectId, username);

        if (userProjectService.canThisUserReadThisProject(username, projectId)) {
            return projectMapper.toDto(projectRepository.readProject(projectId));
        }

        throw new RuntimeException("Security constraints violation");
    }

    public List<ProjectDTO> readProjectsWithIssues(String username) {

        log.info("Reading projects for user {}", username);
        return readProjectsWithIssues(userProjectService.getUserProjects(username));

    }

    public ProjectDTO createProject(ProjectDTO projectDTO, String username) {

        log.info("Creating for user {}", username);


        Project project = projectRepository.createProject(projectMapper.toEntity(projectDTO));
        userProjectService.associateUserToProject(username, project.getId());

        return projectMapper.toDto(project);
    }

    public ProjectDTO updateProject(ProjectDTO projectDTO, String userName) {
        if (!userProjectService.canThisUserReadThisProject(userName, projectDTO.getId())) {
            throw new RuntimeException("Security constraints violation");
        }

        Project updatedProject = projectRepository.updateProject(projectMapper.toEntity(projectDTO));

        return projectMapper.toDto(updatedProject);
    }

    public ProjectDTO patchProject(ProjectDTO projectDTO, String userName) {
        if (!userProjectService.canThisUserReadThisProject(userName, projectDTO.getId())) {
            throw new RuntimeException("Security constraints violation");
        }

        Project dbProject = projectRepository.readProject(projectDTO.getId());

        if (projectDTO.getName() != null) {
            projectDTO.setName(dbProject.getName());
        }

        if (projectDTO.getDescription() != null) {
            projectDTO.setDescription(dbProject.getDescription());
        }

        return projectMapper.toDto(projectRepository.updateProject(dbProject));
    }

    private List<ProjectDTO> readProjectsWithIssues(List<Integer> userProjectIds) {

        return projectRepository.readProjects(userProjectIds)
                .stream()
                .map(projectMapper::toDtoWithIssues)
                .toList();

    }

    public void deleteProject(Integer projectId, String username) {
        if (!userProjectService.canThisUserReadThisProject(username, projectId)) {
            throw new RuntimeException("Security constraints violation");
        }

        projectRepository.deleteProject(projectId);
    }

}
