package it.intesys.academy.service;

import it.intesys.academy.controller.rest.errors.ProjectAccessException;
import it.intesys.academy.domain.Comment;
import it.intesys.academy.domain.Issue;
import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.mapper.CommentMapper;
import it.intesys.academy.mapper.IssueMapper;
import it.intesys.academy.repository.CommentRepository;
import it.intesys.academy.repository.IssueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IssueService {

    private static final Logger log = LoggerFactory.getLogger(IssueService.class);

    private final IssueRepository issueRepository;

    private final CommentRepository commentRepository;

    private final UserProjectService userProjectService;

    private final IssueMapper issueMapper;

    private final CommentMapper commentMapper;

    public IssueService(IssueRepository issueRepository, CommentMapper commentMapper,CommentRepository commentRepository, UserProjectService userProjectService, IssueMapper issueMapper) {

        this.issueRepository = issueRepository;
        this.commentRepository = commentRepository;
        this.userProjectService = userProjectService;
        this.issueMapper = issueMapper;
        this.commentMapper = commentMapper;
    }

    public List<IssueDTO> readIssuesByProjectId(Integer projectId, String userName) {

        log.info("Reading issues for project {}", projectId);

        if (!userProjectService.canThisUserReadThisProject(userName, projectId)) {
            throw new ProjectAccessException("Project Permission error",projectId);
        }

        return issueRepository.findByProjectIdIn(List.of(projectId))
                .stream()
                .map(issueMapper::toDto)
                .collect(Collectors.toList());

    }

    public IssueDTO readIssueWithComments(Integer issueId, String userName) {

        log.info("Reading issue {}", issueId);

        IssueDTO issueDTO = issueMapper.toDto(issueRepository.findIssueById(issueId));

        if (!userProjectService.canThisUserReadThisProject(userName, issueDTO.getProjectId())) {
            throw new ProjectAccessException("Project Permission error",issueDTO.getProjectId());
        }
        List<Comment> comments = commentRepository.findCommentsByIssueId(issueId);
        issueDTO.setComments(comments.stream()
                .map(commentMapper::toDTO)
                .toList()
        );
        return issueDTO;

    }

    public IssueDTO createIssue(IssueDTO issueDTO, String username) {

        log.info("Creating for user {}", username);


        Issue issue = issueRepository.save(issueMapper.toEntity(issueDTO));

        return issueMapper.toDto(issue);
    }

    public IssueDTO updateIssue(IssueDTO issueDTO, String userName) {

        if (!userProjectService.canThisUserReadThisProject(userName, issueDTO.getProjectId())) {
            throw new RuntimeException("Security constraints violation");
        }

        Issue dbIssue = issueRepository.findIssueById(issueDTO.getId());
        if ( dbIssue.getProject().getId() != issueDTO.getProjectId() &&
                !userProjectService.canThisUserReadThisProject(userName, dbIssue.getProject().getId())) {
            throw new ProjectAccessException("Project Permission error",dbIssue.getProject().getId());
        }

        Issue updatedIssue = issueRepository.save(issueMapper.toEntity(issueDTO));

        return issueMapper.toDto(updatedIssue);
    }

    public void deleteIssue(Integer issueId, String username) {
        Issue dbIssue = issueRepository.findIssueById(issueId);
        if (!userProjectService.canThisUserReadThisProject(username, dbIssue.getProject().getId())) {
            throw new ProjectAccessException("Project Permission error",dbIssue.getProject().getId());
        }

        issueRepository.deleteById(issueId);
    }

    public void deleteAllFromIssue(Integer issueId, String username) {
        Issue dbIssue = issueRepository.findIssueById(issueId);
        if (!userProjectService.canThisUserReadThisProject(username, dbIssue.getProject().getId())) {
            throw new ProjectAccessException("Project Permission error",dbIssue.getProject().getId());
        }

        List<Comment> comments = commentRepository.findCommentsByIssueIdIn(List.of(issueId));
        commentRepository.deleteAllById(comments.stream().map(Comment::getId).toList());
        issueRepository.deleteById(issueId);
    }

}
