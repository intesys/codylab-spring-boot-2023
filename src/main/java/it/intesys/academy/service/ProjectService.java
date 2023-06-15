package it.intesys.academy.service;

import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.dto.ProjectDTO;
import it.intesys.academy.repository.IssueRepository;
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

    private final IssueRepository issueRepository;

    private final UserProjectService userProjectService;

    public ProjectService(ProjectRepository projectRepository, IssueRepository issueRepository,
        UserProjectService userProjectService) {

        this.projectRepository = projectRepository;
        this.issueRepository = issueRepository;
        this.userProjectService = userProjectService;
    }

    public ProjectDTO readProjectWithIssue(int projectId, String username) {

        log.info("Reading project {} with issues, user {}", projectId, username);

        if (userProjectService.canThisUserReadThisProject(username, projectId)) {
            ProjectDTO projectDTO = projectRepository.readProject(projectId);
            issueRepository.readIssues(List.of(projectId)).forEach(projectDTO::addIssue);
            return projectDTO;
        }

        throw new RuntimeException("Security constraints violation");

    }

    public ProjectDTO readProject(int projectId, String username) {

        log.info("Reading project {} , user {}", projectId, username);

        if (userProjectService.canThisUserReadThisProject(username, projectId)) {
            return projectRepository.readProject(projectId);
        }

        throw new RuntimeException("Security constraints violation");
    }

    public List<ProjectDTO> readProjectsWithIssues(String username) {

        log.info("Reading projects for user {}", username);
        return readProjectsWithIssues(userProjectService.getUserProjects(username));

    }

    public ProjectDTO updateProject(ProjectDTO projectDTO, String userName) {
        if (!userProjectService.canThisUserReadThisProject(userName, projectDTO.getId())) {
            throw new RuntimeException("Security constraints violation");
        }

        projectRepository.updateProject(projectDTO);

        ProjectDTO dbProject = projectRepository.readProject(projectDTO.getId());

        dbProject.setName(projectDTO.getName());
        dbProject.setDescription(projectDTO.getDescription());

        return dbProject;
    }


    private List<ProjectDTO> readProjectsWithIssues(List<Integer> userProjectIds) {

        List<ProjectDTO> userProjects = projectRepository.readProjects(userProjectIds);

        HashMap<Integer, ProjectDTO> mapProjects = new HashMap<>();

        for (ProjectDTO p: userProjects) {
            mapProjects.put(p.getId(), p);
        }

        List<Integer> projectIds = userProjects.stream()
                                           .map(ProjectDTO::getId)
                                           .toList();

        List<IssueDTO> issues = issueRepository.readIssues(projectIds);

        for (IssueDTO issue : issues) {
            mapProjects.get(issue.getProjectId()).addIssue(issue);
        }

        return userProjects;

    }

        public ProjectDTO createProject(ProjectDTO projectDTO, String username) {

        log.info("Creating for user {}", username);

        Integer createdProjectId = projectRepository.createProject(projectDTO);
        userProjectService.associateUserToProject(username, createdProjectId);

        return projectRepository.readProject(createdProjectId);
    }

}
