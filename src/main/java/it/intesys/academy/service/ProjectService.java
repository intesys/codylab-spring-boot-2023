package it.intesys.academy.service;

import it.intesys.academy.controller.openapi.model.IssueApiDTO;
import it.intesys.academy.controller.openapi.model.ProjectApiDTO;
import it.intesys.academy.controller.rest.errors.ForbiddenException;
import it.intesys.academy.domain.*;
import it.intesys.academy.mapper.CommentMapper;
import it.intesys.academy.mapper.IssueMapper;
import it.intesys.academy.mapper.ProjectMapper;
import it.intesys.academy.repository.*;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ProjectService {

    private static final Logger log = LoggerFactory.getLogger(ProjectService.class);

    private final ProjectRepository projectRepository;

    private final IssueRepository issueRepository;

    private final UserProjectService userProjectService;

    private final ProjectMapper projectMapper;

    private final IssueMapper issueMapper;

    private final CommentRepository commentRepository;

    private final PersonRepository personRepository;

    private final CommentMapper commentMapper;

    private final UserProjectRepository userProjectRepository;

    public ProjectService(PersonRepository personRepository,UserProjectRepository userProjectRepository,ProjectRepository projectRepository, IssueRepository issueRepository, CommentRepository commentRepository,
                          UserProjectService userProjectService, ProjectMapper projectMapper, IssueMapper issueMapper, CommentMapper commentMapper) {

        this.projectRepository = projectRepository;
        this.issueRepository = issueRepository;
        this.userProjectService = userProjectService;
        this.projectMapper = projectMapper;
        this.issueMapper = issueMapper;
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.userProjectRepository = userProjectRepository;
        this.personRepository = personRepository;
    }

    public ProjectApiDTO readProjectWithIssue(int projectId) {

        var username = SecurityUtils.getUserName();

        log.info("Reading project {} with issues, user {}", projectId, username);

        if (userProjectService.canThisUserReadThisProject(username, projectId)) {
            ProjectApiDTO projectDTO = projectMapper.toDto(projectRepository.findById(projectId).get());
            List<Issue> issues= issueRepository.findByProjectIdIn(List.of(projectId));
            for(Issue issue:issues){
                log.info("Reading issue {}, project {}", issue, projectId);
                IssueApiDTO issueDTO = issueMapper.toDto(issue);
                List<Comment> comments= commentRepository.findCommentsByIssueId(issue.getId());

                for(Comment comment : comments){
                    issueDTO.addCommentsItem(commentMapper.toDTO(comment));
                }
                projectDTO.addIssuesItem(issueDTO);
            }
            return projectDTO;
        }

        throw new RuntimeException("Security constraints violation");

    }

    public ProjectApiDTO readProject(int projectId) {
        var username = SecurityUtils.getUserName();
        log.info("Reading project {} , user {}", projectId, username);

        if (userProjectService.canThisUserReadThisProject(username, projectId)) {
            return projectMapper.toDto(projectRepository.findById(projectId).get());
        }

        throw new RuntimeException("Security constraints violation");
    }

    public List<ProjectApiDTO> readProjectsWithIssues() {
        var username = SecurityUtils.getUserName();
        log.info(username);
        log.info("Reading projects for user {}", username);
        return readProjectsWithIssues(userProjectService.getUserProjects(username));

    }

    public ProjectApiDTO createProject(ProjectApiDTO projectDTO) {
        if(SecurityUtils.getRoles().contains("ROLE_ADMIN")){
            throw new ForbiddenException("Create Project not permitted");
        }
        var username = SecurityUtils.getUserName();

        log.info("Creating for user {}", username);
        Project projectN = projectMapper.toEntity(projectDTO);
        Person personN = personRepository.findById(projectDTO.getAuthorId()).get();
        projectN.setUser(personN);

        Project project = projectRepository.save(projectN);
        userProjectService.associateUserToProject(personN.getUsername(), project.getId());

        return projectMapper.toDto(project);
    }

    public ProjectApiDTO updateProject(ProjectApiDTO projectDTO) {
        var userName = SecurityUtils.getUserName();
        if (!userProjectService.canThisUserReadThisProject(userName, projectDTO.getId())) {
            throw new ForbiddenException("Update Project not permitted");
        }

        Project project = projectRepository.save(projectMapper.toEntity(projectDTO));

        return readProjectWithIssue(project.getId());
    }

    public ProjectApiDTO patchProject(ProjectApiDTO projectDTO) {
        var userName = SecurityUtils.getUserName();
        if (!userProjectService.canThisUserReadThisProject(userName, projectDTO.getId())) {
            throw new RuntimeException("Security constraints violation");
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

    private List<ProjectApiDTO> readProjectsWithIssues(List<Integer> userProjectIds) {

        return projectRepository.findByIdIn(userProjectIds)
                .stream()
                .map(projectMapper::toDtoWithIssues)
                .toList();

    }

    public void deleteProject(Integer projectId) {
        var username = SecurityUtils.getUserName();
        if (!userProjectService.canThisUserReadThisProject(username, projectId)) {
            throw new RuntimeException("Security constraints violation");
        }

        projectRepository.deleteById(projectId);
    }

    public void deleteAllFromProject(Integer projectId){
        var username = SecurityUtils.getUserName();
        if (!userProjectService.canThisUserReadThisProject(username, projectId)) {
            throw new RuntimeException("Security constraints violation");
        }
        List<UserProject> userProjects = userProjectRepository.findUserProjectsByPersonUsernameAndProjectId(username,projectId);
        List<Issue> issues = issueRepository.findByProjectIdIn(List.of(projectId));
        List<Integer> issuesIds = issues.stream()
                .map(Issue::getId)
                .toList();
        List<Comment> comments = commentRepository.findCommentsByIssueIdIn(issuesIds);

        commentRepository.deleteAllById(comments.stream().map(Comment::getId).toList());
        issueRepository.deleteAllById(issuesIds);
        userProjectRepository.deleteAllById(userProjects.stream().map(UserProject::getId).toList());
        projectRepository.deleteById(projectId);
    }

}
