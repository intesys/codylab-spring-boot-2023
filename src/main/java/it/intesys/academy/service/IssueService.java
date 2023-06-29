package it.intesys.academy.service;

import it.intesys.academy.controller.openapi.model.IssueApiDTO;
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

    private final CommentService commentService;
    private final UserProjectService userProjectService;

    private final IssueMapper issueMapper;
    private final CommentMapper commentMapper;

    public IssueService(IssueRepository issueRepository, CommentRepository commentRepository, CommentService commentService, UserProjectService userProjectService, IssueMapper issueMapper, CommentMapper commentMapper) {

        this.issueRepository = issueRepository;
        this.commentService = commentService;
        this.userProjectService = userProjectService;
        this.issueMapper = issueMapper;
        this.commentMapper = commentMapper;
    }

    public List<IssueApiDTO> readIssuesByProjectId(Integer projectId, String userName) {

        log.info("Reading issues for project {}", projectId);

        if (!userProjectService.canThisUserReadThisProject(userName, projectId)) {
            throw new ProjectAccessException("Project permission error", projectId);
        }


        return issueRepository.findByProject_Id(projectId)
                .stream()
                .map(issueMapper::toApiDto)
                .collect(Collectors.toList());

    }

    public IssueApiDTO readIssueWithComments(Integer issueId, String userName) {

        log.info("Reading issue {}", issueId);

        IssueApiDTO issueApiDTO = issueMapper.toApiDto(issueRepository.findIssueById(issueId));

        if (!userProjectService.canThisUserReadThisProject(userName, issueApiDTO.getProjectId())) {
            throw new ProjectAccessException("Project permission error", issueApiDTO.getProjectId());
        }

        issueApiDTO.setComments(commentService.readCommentsByIssueId(issueId));
        return issueApiDTO;

    }


    public IssueApiDTO createIssue(IssueApiDTO issueApiDTO, String username) {

        log.info("Creating for user {}", username);


        Issue issue = issueRepository.save(issueMapper.toEntity(issueApiDTO));

        return issueMapper.toApiDto(issue);
    }

    public IssueApiDTO updateIssue(IssueApiDTO issueApiDTO, String userName) {

        if (!userProjectService.canThisUserReadThisProject(userName, issueApiDTO.getProjectId())) {
            throw new ProjectAccessException("Project permission error", issueApiDTO.getProjectId());
        }

        Issue dbIssue = issueRepository.findIssueById(issueApiDTO.getId());
        if ( dbIssue.getProject().getId() != issueApiDTO.getProjectId() &&
                !userProjectService.canThisUserReadThisProject(userName, dbIssue.getProject().getId())) {
            throw new ProjectAccessException("Project permission error", issueApiDTO.getProjectId());
        }

        Issue updatedIssue = issueRepository.save(issueMapper.toEntity(issueApiDTO));

        return issueMapper.toApiDto(updatedIssue);
    }


    public void deleteIssue(Integer issueId, String username) {
        Issue dbIssue = issueRepository.findIssueById(issueId);
        if (!userProjectService.canThisUserReadThisProject(username, dbIssue.getProject().getId())) {
            throw new RuntimeException("Security constraints violation");
        }

        for (Comment comment : commentService.readCommentsByIssueId(issueId).stream().map(commentMapper::toEntity).toList()) {
            commentService.deleteComment(comment.getId(), username);
        }
    }

}
