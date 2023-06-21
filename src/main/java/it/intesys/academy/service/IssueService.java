package it.intesys.academy.service;

import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.mapper.IssueMapper;
import it.intesys.academy.repository.CommentRepository;
import it.intesys.academy.repository.IssueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

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

        IssueDTO createdIssue = issueMapper.toDto(issueRepository.createIssue(issueMapper.toEntity(issueDTO)));

        return issueMapper.toDto(issueRepository.readIssue(createdIssue.getId()));
    }

    public IssueDTO updateIssue(IssueDTO issueDTO, String userName) {

        if (!userProjectService.canThisUserReadThisProject(userName, issueDTO.getProjectId())) {
            throw new RuntimeException("Security constraints violation");
        }

        IssueDTO dbIssue = issueMapper.toDto(issueRepository.readIssue(issueDTO.getId()));
        if ( dbIssue.getProjectId() != issueDTO.getProjectId() && 
                !userProjectService.canThisUserReadThisProject(userName, dbIssue.getProjectId())) {
            throw new RuntimeException("Security constraints violation");
        }

        issueRepository.updateIssue(issueMapper.toEntity(issueDTO));

        return issueMapper.toDto(issueRepository.readIssue(issueDTO.getId()));
    }

    public void deleteIssue(Integer issueId, String username) {
        IssueDTO dbIssue = issueMapper.toDto(issueRepository.readIssue(issueId));
        if (!userProjectService.canThisUserReadThisProject(username, dbIssue.getProjectId())) {
            throw new RuntimeException("Security constraints violation");
        }

        issueRepository.deleteIssue(issueId);
    }

}
