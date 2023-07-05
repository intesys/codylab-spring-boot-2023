package it.intesys.academy.service;

import it.intesys.academy.controller.openapi.model.ProjectApiDTO;
import it.intesys.academy.controller.rest.errors.ForbiddenException;
import it.intesys.academy.controller.rest.errors.ProjectAccessException;
import it.intesys.academy.domain.Issue;
import it.intesys.academy.domain.Project;
import it.intesys.academy.dto.ProjectDTO;
import it.intesys.academy.mapper.IssueMapper;
import it.intesys.academy.mapper.ProjectMapper;
import it.intesys.academy.repository.IssueRepository;
import it.intesys.academy.repository.ProjectRepository;
import it.intesys.academy.repository.ProjectSettingsRepository;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    private final ProjectSettingsRepository projectSettingsRepository;
    public ProjectService(ProjectRepository projectRepository, IssueRepository issueRepository,
                          UserProjectService userProjectService, ProjectMapper projectMapper, IssueMapper issueMapper, IssueService issueService, ProjectSettingsRepository projectSettingsRepository) {

        this.projectRepository = projectRepository;
        this.issueRepository = issueRepository;
        this.userProjectService = userProjectService;
        this.projectMapper = projectMapper;
        this.issueMapper = issueMapper;
        this.issueService = issueService;
        this.projectSettingsRepository = projectSettingsRepository;
    }

    public ProjectApiDTO readProjectWithIssue(int projectId) {

        var username = SecurityUtils.getCurrentUser();

        if (userProjectService.alternativePermissionToProjects(username, projectId)) {
            ProjectApiDTO projectApiDTO = projectMapper.toApiDto(projectRepository.findById(projectId).get());
            issueRepository.findByProject_Id(projectId)
                    .forEach(issue -> projectApiDTO.addIssuesItem(issueMapper.toApiDto(issue)));
            return projectApiDTO;
        }

        throw new RuntimeException("Security constraints violation");

    }

    public ProjectDTO readProject(int projectId) {

        var username = SecurityUtils.getCurrentUser();

        if (userProjectService.canThisUserReadThisProject(username, projectId)) {
            return projectMapper.toDto(projectRepository.findById(projectId).get());
        }

        throw new RuntimeException("Security constraints violation");
    }

    public List<ProjectApiDTO> readProjectsWithIssues() {

        if (!SecurityUtils.hasAuthority("ROLE_ADMIN")) {
            throw new ForbiddenException("Cannot delete project");
        }

        return readAllProjects();

    }

    public ProjectApiDTO createProject(ProjectApiDTO projectApiDTO) {

        var username = SecurityUtils.getCurrentUser();

        Project project = projectRepository.save(projectMapper.toEntity(projectApiDTO));
        userProjectService.associateUserToProject(username, project.getId());

        return projectMapper.toApiDto(project);
    }

    public ProjectApiDTO updateProject(ProjectApiDTO projectApiDTO) {

        var userName = SecurityUtils.getCurrentUser();

        if (!userProjectService.canThisUserReadThisProject(userName, projectApiDTO.getId())) {
            throw new ProjectAccessException("Project permission error", projectApiDTO.getId());
        }

        Project updatedProject = projectRepository.save(projectMapper.toEntity(projectApiDTO));

        return projectMapper.toApiDto(updatedProject);
    }

    public ProjectDTO patchProject(ProjectDTO projectDTO) {

        var userName = SecurityUtils.getCurrentUser();

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

/*    private List<ProjectApiDTO> readProjectsWithIssues(List<Integer> userProjectIds) {
        List<ProjectApiDTO> projectList = new ArrayList<>();
        for (Project project: projectRepository.findByIdIn(userProjectIds)) {
            project.setDetails(projectSettingsRepository.getProjectSettingsByProjectId(project.getId()));
            projectList.add(projectMapper.toApiDtoWithIssues(project));
        }
        return projectList;


    }*/
    private List<ProjectApiDTO> readAllProjects(){

        List<ProjectApiDTO> projectList = new ArrayList<>();
        for (Project project: projectRepository.findAll()) {
            project.setDetails(projectSettingsRepository.findByProjectId(project.getId()));
            projectList.add(projectMapper.toApiDtoWithIssues(project));
        }
        return projectList;
    }


@Secured("ROLE_ADMIN")
    public void deleteProject(Integer projectId) {

        var username = SecurityUtils.getCurrentUser();

        for (Issue issue : issueService.readIssuesByProjectId(projectId).stream().map(issueMapper::toEntity).toList()) {
            issueService.deleteIssue(issue.getId());
        }
        userProjectService.deleteUserProject(username, projectId);
        projectRepository.deleteById(projectId);
    }

}
