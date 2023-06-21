package it.intesys.academy.service;

import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.dto.ProjectDTO;
import it.intesys.academy.repository.CommentRepository;
import it.intesys.academy.repository.IssueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import it.intesys.academy.entity.Issue;
import it.intesys.academy.mapper.IssueMapper;

import java.util.List;

@Service
public class IssueService {

    private static final Logger log = LoggerFactory.getLogger(IssueService.class);

    private final IssueRepository issueRepository;

    private final CommentRepository commentRepository;

    private final UserProjectService userProjectService;

    private final IssueMapper issueMapper;

    public IssueService(IssueRepository issueRepository, CommentRepository commentRepository, UserProjectService userProjectService, IssueMapper issueMapper) {
        this.issueMapper = issueMapper;
        this.issueRepository = issueRepository;
        this.commentRepository = commentRepository;
        this.userProjectService = userProjectService;
    }

    public List<IssueDTO> readIssuesByProjectId(Integer projectId, String userName) {

        log.info("Reading issues for project {}", projectId);

        if (!userProjectService.canThisUserReadThisProject(userName, projectId)) {
            throw new RuntimeException("Security constraints violation");
        }

        return issueRepository.readIssues(List.of(projectId)).stream()
                .map(issueMapper::toDto)
                .toList();

    }

    public IssueDTO readIssueWithComments(Integer issueId, String userName) {

        log.info("Reading issue {}", issueId);

        IssueDTO issueDTO = issueMapper.toDto(issueRepository.readIssue(issueId));

        if (!userProjectService.canThisUserReadThisProject(userName, issueDTO.getProjectId())) {
            throw new RuntimeException("Security constraints violation");
        }

        issueDTO.setComments(commentRepository.findCommentsByIssueId(issueId));
        return issueDTO;

    }

    public IssueDTO createIssue(IssueDTO issueDTO, String username) {

        log.info("Creating for user {}", username);

        Issue issue = issueMapper.toEntity(issueDTO);
        issue = issueRepository.createIssue(issue);

        return issueMapper.toDto(issue);
    }

    public IssueDTO updateIssue(IssueDTO issueDTO, String userName) {

        if (!userProjectService.canThisUserReadThisProject(userName, issueDTO.getId())) {
            throw new RuntimeException("Security constraints violation");
        }

        Issue issue = issueMapper.toEntity(issueDTO);
        issue = issueRepository.updateIssue(issue);


        return issueMapper.toDto(issue);
    }

    public void deleteIssue(Integer issueId, String username) {
        Issue dbIssue = issueRepository.readIssue(issueId);
        if (!userProjectService.canThisUserReadThisProject(username, dbIssue.getProject().getId())) {
            throw new RuntimeException("Security constraints violation");
        }

        issueRepository.deleteIssue(issueId);
    }

}
