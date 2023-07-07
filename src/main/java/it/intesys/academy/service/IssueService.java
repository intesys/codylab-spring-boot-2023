package it.intesys.academy.service;

import it.intesys.academy.controller.openapi.model.IssueApiDTO;
import it.intesys.academy.controller.rest.errors.ForbiddenException;
import it.intesys.academy.controller.rest.errors.ProjectAccessException;
import it.intesys.academy.domain.Issue;
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

    private final CommentService commentService;

    public IssueService(IssueRepository issueRepository, CommentRepository commentRepository, UserProjectService userProjectService, IssueMapper issueMapper, CommentService commentService) {

        this.issueRepository = issueRepository;
        this.commentRepository = commentRepository;
        this.userProjectService = userProjectService;
        this.issueMapper = issueMapper;
        this.commentService = commentService;
    }

    public List<IssueApiDTO> readIssuesByProjectId(Integer projectId) {

        log.info("Reading issues for project {}", projectId);

        var userName = SecurityUtils.getCurrentUser();


        if (!userProjectService.canThisUserReadThisProject(userName, projectId)) {
            throw new ProjectAccessException("Project permission error", projectId);
        }

        return issueRepository.findByProject_Id(projectId)
                .stream()
                .map(issueMapper::toApiDto)
                .collect(Collectors.toList());

    }

    public IssueApiDTO readIssueWithComments(Integer issueId) {

        log.info("Reading issue {}", issueId);

        var userName = SecurityUtils.getCurrentUser();

        IssueApiDTO issueApiDTO = issueMapper.toApiDto(issueRepository.findIssueById(issueId));

        if (!userProjectService.canThisUserReadThisProject(userName, issueApiDTO.getProjectId())) {
            throw new RuntimeException("Security constraints violation");
        }

        issueApiDTO.setComments(commentService.readCommentsByIssueId(issueId));
        return issueApiDTO;

    }

    public IssueApiDTO createIssue(IssueApiDTO issueApiDTO) {

        var username = SecurityUtils.getCurrentUser();

        log.info("Creating for user {}", username);

        Issue issue = issueRepository.save(issueMapper.toEntity(issueApiDTO));

        return issueMapper.toApiDto(issue);
    }

    public IssueApiDTO updateIssue(IssueApiDTO issueApiDTO) {

        var username = SecurityUtils.getCurrentUser();
        if (!SecurityUtils.hasAuthority("ROLE_ADMIN")) {
            throw new ForbiddenException("Security constraints violation");}
            Issue dbIssue = issueRepository.findIssueById(issueApiDTO.getId());
            if (dbIssue.getProject().getId() != issueApiDTO.getProjectId() &&
                    !userProjectService.canThisUserReadThisProject(username, dbIssue.getProject().getId())) {
                throw new RuntimeException("Cannot update issue");
            }

            Issue updatedIssue = issueRepository.save(issueMapper.toEntity(issueApiDTO));

            return issueMapper.toApiDto(updatedIssue);
        }

    public void deleteIssue(Integer issueId) {
        var username = SecurityUtils.getCurrentUser();
        Issue dbIssue = issueRepository.findIssueById(issueId);
        if (!SecurityUtils.hasAuthority("ROLE_ADMIN")) {
            throw new ForbiddenException("Cannot delete issue");

            //delete comments
        }
        issueRepository.deleteIssueById(issueId);


    }
}