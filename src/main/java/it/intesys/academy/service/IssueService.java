package it.intesys.academy.service;

import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.dto.ProjectDTO;
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

    private final ProjectService projectService;

    private final UserProjectService userProjectService;

    public IssueService(IssueRepository issueRepository, UserProjectService userProjectService, ProjectService projectService) {
        this.projectService = projectService;
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

    public IssueDTO getIssue(Integer issueId, String userName){
        List<ProjectDTO> projects = projectService.readProjectsWithIssues(userName);

        List<Integer> projectsId = projects.stream().map(ProjectDTO::getId).toList();

        IssueDTO issue = issueRepository.readIssue(issueId);
        //log.info(issue.getNome());

        if(projectsId.contains(issue.getProjectId())){
            IssueDTO result =  issueRepository.readIssue(issueId);
            return result;
        }
        throw new RuntimeException("Error during reading Issue");
    }

    public IssueDTO createIssue(IssueDTO issueDTO, Integer projectId, String username){
        if(!userProjectService.canThisUserReadThisProject(username,projectId)){
            throw new RuntimeException("CreateIssues error");
        }
        int issueId = issueRepository.createIssue(issueDTO);
        return issueRepository.readIssue(issueId);
    }

    public IssueDTO updateIssue(IssueDTO issueDTO, Integer projectId, String username, Integer issueId){
        if(!userProjectService.canThisUserReadThisProject(username,projectId)){
            throw new RuntimeException("CreateIssues error");
        }
        if(!userProjectService.canThisUserReadThisProject(username,issueDTO.getProjectId())){
            throw new RuntimeException("CreateIssues error");
        }
        int projectIssueId = issueRepository.readIssue(issueId).getProjectId();
        if(!(projectIssueId == projectId)){
            throw new RuntimeException("CreateIssues error");
        }
        issueRepository.updateIssue(issueDTO);
        return issueRepository.readIssue(issueId);

    }

    public void deleteIssue(Integer projectId, String username, Integer issueId){
        if(!userProjectService.canThisUserReadThisProject(username,projectId)){
            throw new RuntimeException("CreateIssues error");
        }
        int projectIssueId = issueRepository.readIssue(issueId).getProjectId();
        if(!(projectIssueId == projectId)){
            throw new RuntimeException("CreateIssues error");
        }
        issueRepository.deleteIssue(issueId);
    }
}