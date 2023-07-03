package it.intesys.academy.service;

import it.intesys.academy.controller.rest.errors.ProjectPermissionException;
import it.intesys.academy.domain.Issue;
import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.mapper.IssueMapper;
import it.intesys.academy.repository.CommentRepository;
import it.intesys.academy.repository.IssueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.security.access.annotation.Secured;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IssueService {

    private static final Logger log = LoggerFactory.getLogger(IssueService.class);

    private final IssueRepository issueRepository;

    private final CommentRepository commentRepository;

    private final UserProjectService userProjectService;

    private final IssueMapper issueMapper;

    public IssueService(IssueRepository issueRepository, CommentRepository commentRepository, UserProjectService userProjectService, IssueMapper issueMapper) {

        this.issueRepository = issueRepository;
        this.commentRepository = commentRepository;
        this.userProjectService = userProjectService;
        this.issueMapper = issueMapper;
    }

    public List<IssueDTO> readIssuesByProjectId(Integer projectId) {

        log.info("Reading issues for project {}", projectId);

        var userName = SecurityUtils.getUserName();

        if (!userProjectService.canThisUserReadThisProject(userName, projectId)) {
            throw new ProjectPermissionException("Access to project " + projectId + " forbidden");
        }

        return issueRepository.readIssues(List.of(projectId))
                .stream()
                .map(issueMapper::toDto)
                .collect(Collectors.toList());

    }

    public IssueDTO readIssueWithComments(Integer issueId, String userName) {

        log.info("Reading issue {}", issueId);

        IssueDTO issueDTO = issueMapper.toDto(issueRepository.readIssue(issueId));

        if (!userProjectService.canThisUserReadThisProject(userName, issueDTO.getProjectId())) {
            throw new ProjectPermissionException("Access to project " + issueDTO.getProjectId() + " forbidden");
        }

        issueDTO.setComments(commentRepository.findCommentsByIssueId(issueId));
        return issueDTO;

    }





    @Secured("ROLE_ADMIN")
    public IssueDTO createIssue(IssueDTO issueDTO) {

        var userName = SecurityUtils.getUserName();
        log.info("Creating for user {}", userName);


        Issue issue = issueRepository.createIssue(issueMapper.toEntity(issueDTO));

        return issueMapper.toDto(issue);
    }

    public IssueDTO updateIssue(IssueDTO issueDTO, String userName) {

        if (!userProjectService.canThisUserReadThisProject(userName, issueDTO.getId())) {
            throw new ProjectPermissionException("Access to project " + issueDTO.getProjectId() + " forbidden");
        }

        Issue dbIssue = issueRepository.readIssue(issueDTO.getId());
        if ( dbIssue.getProject().getId() != issueDTO.getProjectId() &&
                !userProjectService.canThisUserReadThisProject(userName, dbIssue.getProject().getId())) {
            throw new ProjectPermissionException("Access to project " + issueDTO.getProjectId() + " forbidden");
        }

        Issue updatedIssue = issueRepository.updateIssue(issueMapper.toEntity(issueDTO));

        return issueMapper.toDto(updatedIssue);
    }

    public void deleteIssue(Integer issueId, String username) {
        Issue dbIssue = issueRepository.readIssue(issueId);
        if (!userProjectService.canThisUserReadThisProject(username, dbIssue.getProject().getId())) {
            throw new ProjectPermissionException("Access to project " + dbIssue.getProject().getId() + " forbidden");
        }

        issueRepository.deleteIssue(issueId);
    }

}
