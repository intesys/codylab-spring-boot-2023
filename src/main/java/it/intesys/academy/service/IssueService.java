package it.intesys.academy.service;

import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.dto.ProjectDTO;
import it.intesys.academy.entity.Issues;
import it.intesys.academy.mapper.IssueMapper;
import it.intesys.academy.repository.IssueRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class IssueService {

    private static final Logger log = LoggerFactory.getLogger(IssueService.class);

    private final IssueRepository issueRepository;

    private final ProjectService projectService;

    private final UserProjectService userProjectService;

    private final IssueMapper issueMapper;

    public IssueService(IssueRepository issueRepository,IssueMapper issueMapper, UserProjectService userProjectService, ProjectService projectService) {
        this.projectService = projectService;
        this.issueRepository = issueRepository;
        this.userProjectService = userProjectService;
        this.issueMapper = issueMapper;
    }

    public List<IssueDTO> readIssues(Integer projectId, String userName) {
        if(userProjectService.canThisUserReadThisProject(userName,projectId)){
            List<Issues> issues= (issueRepository.readIssues(List.of(projectId)));
            return issues.stream()
                    .map(issueMapper::toDTO)
                    .toList();
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

        Issues issue = issueRepository.readIssue(issueId);
        //log.info(issue.getNome());

        if(projectsId.contains(issue.getProjectid())){
            Issues result =  issueRepository.readIssue(issueId);
            return issueMapper.toDTO(result);
        }
        throw new RuntimeException("Error during reading Issue");
    }

    public IssueDTO createIssue(IssueDTO issueDTO, String username){
        if(!userProjectService.canThisUserReadThisProject(username, issueDTO.getProjectId())){
            throw new RuntimeException("CreateIssues error");
        }
        return issueMapper.toDTO(issueRepository.createIssue(issueMapper.toEntity(issueDTO)));
    }

    public IssueDTO updateIssue(IssueDTO issueDTO, String username, Integer issueId){
        int projectIssueId = issueRepository.readIssue(issueId).getProjectid();
        if(!userProjectService.canThisUserReadThisProject(username, projectIssueId)){
            throw new RuntimeException("CreateIssues error");
        }
        if(!userProjectService.canThisUserReadThisProject(username, issueDTO.getProjectId())){
            throw new RuntimeException("CreateIssues error");
        }
        if(!(issueDTO.getId() == issueId)){
            throw new RuntimeException("CreateIssues error");
        }
        issueRepository.updateIssue(issueMapper.toEntity(issueDTO));
        return issueMapper.toDTO(issueRepository.readIssue(issueId));

    }

    public void deleteIssue(String username, Integer issueId){
        int projectIssueId = issueRepository.readIssue(issueId).getProjectid();
        if(!userProjectService.canThisUserReadThisProject(username, projectIssueId)){
            throw new RuntimeException("CreateIssues error");
        }
        issueRepository.deleteIssue(issueId);
    }
}