package it.intesys.academy.service;

import it.intesys.academy.controller.rest.errors.ProjectAccessException;
import it.intesys.academy.domain.Issue;
import it.intesys.academy.domain.Project;
import it.intesys.academy.dto.ProjectDTO;
import it.intesys.academy.mapper.IssueMapper;
import it.intesys.academy.mapper.ProjectMapper;
import it.intesys.academy.repository.IssueRepository;
import it.intesys.academy.repository.ProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private static final Logger log = LoggerFactory.getLogger(ProjectService.class);

    private final ProjectRepository projectRepository;

    private final IssueRepository issueRepository;

    private final UserProjectService userProjectService;

    private final ProjectMapper projectMapper;

    private final IssueMapper issueMapper;
    private final IssueService issueService;

    public ProjectService(ProjectRepository projectRepository, IssueRepository issueRepository,
                          UserProjectService userProjectService, ProjectMapper projectMapper, IssueMapper issueMapper, IssueService issueService) {

        this.projectRepository = projectRepository;
        this.issueRepository = issueRepository;
        this.userProjectService = userProjectService;
        this.projectMapper = projectMapper;
        this.issueMapper = issueMapper;
        this.issueService = issueService;
    }

    public ProjectDTO readProjectWithIssue(int projectId, String username) {

        log.info("Reading project {} with issues, user {}", projectId, username);

        if (userProjectService.canThisUserReadThisProject(username, projectId)) {
            ProjectDTO projectDTO = projectMapper.toDto(projectRepository.findById(projectId).get());
            issueRepository.findByProject_Id(projectId)
                    .forEach(issue -> projectDTO.addIssue(issueMapper.toDto(issue)));
            return projectDTO;
        }

        throw new RuntimeException("Security constraints violation");

    }

    public ProjectDTO readProject(int projectId, String username) {

        log.info("Reading project {} , user {}", projectId, username);

        if (userProjectService.canThisUserReadThisProject(username, projectId)) {
            return projectMapper.toDto(projectRepository.findById(projectId).get());
        }

        throw new RuntimeException("Security constraints violation");
    }

    public List<ProjectDTO> readProjectsWithIssues(String username) {

        log.info("Reading projects for user {}", username);
        return readProjectsWithIssues(userProjectService.getUserProjects(username));

    }

    public ProjectDTO createProject(ProjectDTO projectDTO, String username) {

        log.info("Creating for user {}", username);


        Project project = projectRepository.save(projectMapper.toEntity(projectDTO));
        userProjectService.associateUserToProject(username, project.getId());

        return projectMapper.toDto(project);
    }

    public ProjectDTO updateProject(ProjectDTO projectDTO, String userName) {
        if (!userProjectService.canThisUserReadThisProject(userName, projectDTO.getId())) {
            throw new ProjectAccessException("Project permission error", projectDTO.getId());
        }

        Project updatedProject = projectRepository.save(projectMapper.toEntity(projectDTO));

        return projectMapper.toDto(updatedProject);
    }

    public ProjectDTO patchProject(ProjectDTO projectDTO, String userName) {
        if (!userProjectService.canThisUserReadThisProject(userName, projectDTO.getId())) {
            throw new ProjectAccessException("Project permission error", projectDTO.getId());
        }

        Project dbProject = projectRepository.findById(projectDTO.getId()).get();

        if (projectDTO.getName() != null) {
            projectDTO.setName(dbProject.getName());
        }

        if (projectDTO.getDescription() != null) {
            projectDTO.setDescription(dbProject.getDescription());
        }

        return projectMapper.toDto(projectRepository.save(dbProject));
    }

    private List<ProjectDTO> readProjectsWithIssues(List<Integer> userProjectIds) {

        return projectRepository.findByIdIn(userProjectIds)
                .stream()
                .map(projectMapper::toDtoWithIssues)
                .toList();

    }

    public void deleteProject(Integer projectId, String username) {
        if (!userProjectService.canThisUserReadThisProject(username, projectId)) {
            throw new ProjectAccessException("Project permission error", projectId);
        }
        for (Issue issue : issueService.readIssuesByProjectId(projectId, username).stream().map(issueMapper::toEntity).toList()) {
            issueService.deleteIssue(issue.getId(), username);
        }
        userProjectService.deleteUserProject(username, projectId);
        projectRepository.deleteById(projectId);
    }

}
