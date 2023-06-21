package it.intesys.academy.mapper;

import it.intesys.academy.domain.Project;
import it.intesys.academy.dto.ProjectDTO;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {

    private final IssueMapper issueMapper;

    public ProjectMapper(IssueMapper issueMapper) {
        this.issueMapper = issueMapper;
    }

    public ProjectDTO toDto(Project project) {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(project.getId());
        projectDTO.setName(project.getName());
        projectDTO.setDescription(project.getDescription());
        return projectDTO;
    }

    public ProjectDTO toDtoWithIssues(Project project) {
        ProjectDTO projectDTO = toDto(project);
        project.getIssues().forEach(issue -> projectDTO.addIssue(issueMapper.toDto(issue)));
        return projectDTO;
    }

    public Project toEntity(ProjectDTO projectDTO) {
        Project project = new Project();
        project.setId(projectDTO.getId());
        project.setName(projectDTO.getName());
        project.setDescription(projectDTO.getDescription());
        return project;
    }
}
