package it.intesys.academy.service;

import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.repository.IssueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IssueService {

    private static final Logger log = LoggerFactory.getLogger(IssueService.class);

    private final IssueRepository issueRepository;

    private final UserProjectService userProjectService;

    public IssueService(IssueRepository issueRepository, UserProjectService userProjectService) {

        this.issueRepository = issueRepository;
        this.userProjectService = userProjectService;
    }

    public List<IssueDTO> readIssues(Integer projectId, String userName) {
        if(userProjectService.canThisUserReadThisProject(userName,projectId)){
            return issueRepository.readIssues(List.of(projectId));
        }
        throw new RuntimeException("Error during reading Issues");
    }

    public IssueDTO readIssue(Integer projectId, String userName, Integer issueId) {
        List<IssueDTO> issues = readIssues(projectId,userName);
        for(IssueDTO issue: issues){
            if(issue.getId()==issueId){
                return issue;
            }
        }
        throw new RuntimeException("Error during reading Issue");
    }

}
