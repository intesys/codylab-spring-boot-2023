package it.intesys.academy.service;

import it.intesys.academy.dto.IssueDTO;
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

    public IssueService(IssueRepository issueRepository, CommentRepository commentRepository) {

        this.issueRepository = issueRepository;
        this.commentRepository = commentRepository;
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

}
