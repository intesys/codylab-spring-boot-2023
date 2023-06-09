package it.intesys.academy.service;

import it.intesys.academy.dto.CommentDTO;
import it.intesys.academy.dto.IssueDTO;
import it.intesys.academy.dto.ProjectDTO;
import it.intesys.academy.repository.IssueRepository;
import it.intesys.academy.repository.ProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProjectService {

    private static final Logger log = LoggerFactory.getLogger(ProjectService.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final SettingsService settingsService;

    private final ProjectRepository projectRepository;

    private final IssueRepository issueRepository;

    public ProjectService(NamedParameterJdbcTemplate jdbcTemplate, SettingsService settingsService, ProjectRepository projectRepository, IssueRepository issueRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.settingsService = settingsService;
        this.projectRepository = projectRepository;
        this.issueRepository = issueRepository;
    }

    public List<ProjectDTO> readProjects(String username) {

        List<ProjectDTO> projects = projectRepository.searchProjects(settingsService.getUserProjects(username));

        List<Integer> projectIds = projects.stream()
                                           .map(ProjectDTO::getId)
                                           .toList();

        Map<Integer,ProjectDTO> projectById = new HashMap<>();
        for(ProjectDTO project:projects){
            projectById.put(project.getId(),project);
        }

        List<IssueDTO> issues = issueRepository.searchIssues(projectIds);

        for(IssueDTO issue:issues){
            ProjectDTO project = projectById.get(issue.getProjectId());
            if(project!=null){
                project.addIssue(issue);
            }
        }

        return projects;

    }

    public ProjectDTO readProject(Integer projectId){
        return (projectRepository.searchProject(projectId)).get(0);
    }

    public List<IssueDTO> readIssues(Integer id){
        return issueRepository.readIssuesForProject(id);

    }

    public List<CommentDTO> readComments(Integer id){
        List<CommentDTO>comments = new ArrayList<>();

        jdbcTemplate.query("SELECT id,descrizione,author,issueId FROM Comments WHERE issueId = (:issue)",
                Map.of("issue",id),
                (resultset)->{
                    comments.add(new CommentDTO(
                            resultset.getInt("id"),
                            resultset.getString("descrizione"),
                            resultset.getString("author"),
                            resultset.getInt("issueId")
                    ));
                }
        );

        return comments;
    }

}
