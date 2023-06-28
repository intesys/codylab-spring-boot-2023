package it.intesys.academy.service;

import it.intesys.academy.controller.rest.errors.ProjectAccessException;
import it.intesys.academy.domain.Issue;
import it.intesys.academy.dto.IssueDTO;
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

    private static final Logger log = LoggerFactory.getLogger (IssueService.class);

    private final IssueRepository issueRepository;

    private final CommentRepository commentRepository;

    private final UserProjectService userProjectService;

    private final IssueMapper issueMapper;

    public IssueService (IssueRepository issueRepository, CommentRepository commentRepository, UserProjectService userProjectService, IssueMapper issueMapper) {

        this.issueRepository = issueRepository;
        this.commentRepository = commentRepository;
        this.userProjectService = userProjectService;
        this.issueMapper = issueMapper;
    }

    public List<IssueDTO> readIssuesByProjectId(Integer projectId) {

        log.info ("Reading issues for project {}", projectId);

        var userName= SecurityUtils.getCurrentUser();


        if (!userProjectService.canThisUserReadThisProject(userName, projectId)) {
            throw new ProjectAccessException("Project permission error", projectId);
        }

        return issueRepository.findAllById (List.of (projectId))
                .stream()
                .map(issueMapper::toDto)
                .collect(Collectors.toList());

    }

    public IssueDTO readIssueWithComments(Integer issueId) {

        log.info("Reading issue {}", issueId);

        var userName= SecurityUtils.getCurrentUser();

        IssueDTO issueDTO = issueMapper.toDto(issueRepository.findIssueById(issueId));

        if (!userProjectService.canThisUserReadThisProject(userName, issueDTO.getProjectId())) {
            throw new RuntimeException("Security constraints violation");
        }

        issueDTO.setComments(commentRepository.findCommentsByIssueId(issueId));
        return issueDTO;

    }

    public IssueDTO createIssue(IssueDTO issueDTO) {

        var username= SecurityUtils.getCurrentUser();

        log.info("Creating for user {}", username);


        Issue issue = issueRepository.createIssue(issueMapper.toEntity(issueDTO));

        return issueMapper.toDto(issue);
    }

    public IssueDTO updateIssue(IssueDTO issueDTO, String userName) {

        if (!userProjectService.canThisUserReadThisProject(userName, issueDTO.getId())) {
            throw new RuntimeException("Security constraints violation");
        }

        Issue dbIssue = issueRepository.readIssue(issueDTO.getId());
        if ( dbIssue.getProject().getId() != issueDTO.getProjectId() &&
                !userProjectService.canThisUserReadThisProject(userName, dbIssue.getProject().getId())) {
            throw new RuntimeException("Security constraints violation");
        }

        Issue updatedIssue = issueRepository.updateIssue(issueMapper.toEntity(issueDTO));

        return issueMapper.toDto(updatedIssue);
    }

    public void deleteIssue(Integer issueId, String username) {
        Issue dbIssue = issueRepository.readIssue(issueId);
        if (!userProjectService.canThisUserReadThisProject(username, dbIssue.getProject().getId())) {
            throw new RuntimeException ("Security constraints violation");
        }

        issueRepository.deleteIssue(issueId);
    }

}