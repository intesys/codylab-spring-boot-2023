package it.intesys.academy.service;

import it.intesys.academy.domain.Comment;
import it.intesys.academy.domain.Issue;
import it.intesys.academy.domain.Project;
import it.intesys.academy.domain.UserProject;
import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.dto.ProjectDTO;
import it.intesys.academy.mapper.CommentMapper;
import it.intesys.academy.mapper.IssueMapper;
import it.intesys.academy.mapper.ProjectMapper;
import it.intesys.academy.repository.CommentRepository;
import it.intesys.academy.repository.IssueRepository;
import it.intesys.academy.repository.ProjectRepository;
import it.intesys.academy.repository.UserProjectRepository;
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

    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;

    private final UserProjectRepository userProjectRepository;

    public ProjectService(UserProjectRepository userProjectRepository,ProjectRepository projectRepository, IssueRepository issueRepository, CommentRepository commentRepository,
                          UserProjectService userProjectService, ProjectMapper projectMapper, IssueMapper issueMapper, CommentMapper commentMapper) {

        this.projectRepository = projectRepository;
        this.issueRepository = issueRepository;
        this.userProjectService = userProjectService;
        this.projectMapper = projectMapper;
        this.issueMapper = issueMapper;
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.userProjectRepository = userProjectRepository;
    }

    public ProjectDTO readProjectWithIssue(int projectId, String username) {

        log.info("Reading project {} with issues, user {}", projectId, username);

        if (userProjectService.canThisUserReadThisProject(username, projectId)) {
            ProjectDTO projectDTO = projectMapper.toDto(projectRepository.findById(projectId).get());
            List<Issue> issues= issueRepository.findByProjectIdIn(List.of(projectId));
            for(Issue issue:issues){
                IssueDTO issueDTO = issueMapper.toDto(issue);
                List<Comment> comments= commentRepository.findCommentsByIssueId(issueDTO.getId());
                for(Comment comment : comments){
                    issueDTO.addComment(commentMapper.toDTO(comment));
                }
                projectDTO.addIssue(issueDTO);
            }
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
            throw new RuntimeException("Security constraints violation");
        }

        Project updatedProject = projectRepository.save(projectMapper.toEntity(projectDTO));

        return projectMapper.toDto(updatedProject);
    }

    public ProjectDTO patchProject(ProjectDTO projectDTO, String userName) {
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

    private List<ProjectDTO> readProjectsWithIssues(List<Integer> userProjectIds) {

        return projectRepository.findByIdIn(userProjectIds)
                .stream()
                .map(projectMapper::toDtoWithIssues)
                .toList();

    }

    public void deleteProject(Integer projectId, String username) {
        if (!userProjectService.canThisUserReadThisProject(username, projectId)) {
            throw new RuntimeException("Security constraints violation");
        }

        projectRepository.deleteById(projectId);
    }

    public void deleteAllFromProject(Integer projectId, String username){
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
