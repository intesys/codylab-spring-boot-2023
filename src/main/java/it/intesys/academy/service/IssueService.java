package it.intesys.academy.service;

import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.repository.CommentRepository;
import it.intesys.academy.repository.IssueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class IssueService {

    private static final Logger log = LoggerFactory.getLogger(IssueService.class);

    private final IssueRepository issueRepository;

    private final CommentRepository commentRepository;

    private final ProjectService projectService;

    private final UserProjectService userProjectService;
    public IssueService(IssueRepository issueRepository, CommentRepository commentRepository, ProjectService projectService, UserProjectService userProjectService) {
        this.issueRepository = issueRepository;
        this.commentRepository = commentRepository;
        this.projectService = projectService;
        this.userProjectService = userProjectService;
    }

    public IssueDTO readIssue(Integer issueId) {


        return issueRepository.readIssue(issueId);

    }

    public List<IssueDTO> readIssues(Integer projectId) {

        log.info("Reading issues for project {}", projectId);

        return issueRepository.readIssues(List.of(projectId));

    }

    public IssueDTO readIssueWithComments(Integer issueId) {

        log.info("Reading issue {}", issueId);

        IssueDTO issueDTO = issueRepository.readIssue(issueId);
        issueDTO.setComments(commentRepository.findCommentsByIssueId(issueId));
        return issueDTO;

    }

    /**
     * 1)creo la issue nella table Issues,
     * 2)
     *
     * @param issueDTO
     * @param username
     * @return
     */
    public IssueDTO createIssue(IssueDTO issueDTO, String username){

        log.info("creating for user {}", username);
        Integer createdIssueId= issueRepository.createIssue(issueDTO);
        return issueRepository.readIssue(createdIssueId);

    }

    public void updateIssue(IssueDTO issueDTO)
    {
        issueRepository.updateIssue(issueDTO);
    }
    public void deleteIssue(Integer issueId) {
        issueRepository.deleteIssue(issueId);
    }

}
