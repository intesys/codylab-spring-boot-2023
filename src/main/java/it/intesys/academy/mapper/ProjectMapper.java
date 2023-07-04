package it.intesys.academy.mapper;

import it.intesys.academy.controller.openapi.model.ProjectApiDTO;
import it.intesys.academy.domain.Project;
import it.intesys.academy.dto.ProjectDTO;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {

    private final IssueMapper issueMapper;
    private final ProjectSettingsMapper projectSettingsMapper;

    public ProjectMapper(IssueMapper issueMapper, ProjectSettingsMapper projectSettingsMapper) {
        this.issueMapper = issueMapper;
        this.projectSettingsMapper = projectSettingsMapper;
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

    public ProjectApiDTO toApiDtoWithIssues(Project project) {

        ProjectApiDTO projectApiDTO = toApiDto(project);

        project.getIssues().forEach(issue -> projectApiDTO.addIssuesItem(issueMapper.toApiDto(issue)));

        return projectApiDTO;
    }

    public Project toEntity(ProjectDTO projectDTO) {
        Project project = new Project();
        project.setId(projectDTO.getId());
        project.setName(projectDTO.getName());
        project.setDescription(projectDTO.getDescription());
        return project;
    }

    public Project toEntity(ProjectApiDTO projectApiDTO) {
        Project project = new Project();
        project.setId(projectApiDTO.getId());
        project.setName(projectApiDTO.getName());
        project.setDescription(projectApiDTO.getDescription());
        return project;
    }

    public ProjectApiDTO toApiDto(Project project) {
        ProjectApiDTO projectApiDTO = new ProjectApiDTO();
        projectApiDTO.setId(project.getId());
        projectApiDTO.setName(project.getName());
        projectApiDTO.setDescription(project.getDescription());
        projectApiDTO.setProjectDetail(projectSettingsMapper.toApiDto(project.getDetails()));
        return projectApiDTO;
    }
}
