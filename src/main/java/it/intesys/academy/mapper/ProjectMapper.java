package it.intesys.academy.mapper;

import it.intesys.academy.controller.openapi.model.ProjectApiDTO;
import it.intesys.academy.domain.Person;
import it.intesys.academy.domain.Project;
import it.intesys.academy.dto.ProjectDTO;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {

    private final IssueMapper issueMapper;

    public ProjectMapper(IssueMapper issueMapper) {
        this.issueMapper = issueMapper;
    }

    /**public ProjectDTO toDto(Project project) {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(project.getId());
        projectDTO.setName(project.getName());
        projectDTO.setDescription(project.getDescription());
        projectDTO.setAuthorid(project.getUser().getId());
        return projectDTO;
    }*/

    public ProjectApiDTO toDtoWithIssues(Project project) {
        ProjectApiDTO projectDTO = toDto(project);
        project.getIssues().forEach(issue -> projectDTO.addIssuesItem(issueMapper.toDto(issue)));
        return projectDTO;
    }

    public Project toEntity(ProjectDTO projectDTO) {
        Project project = new Project();
        project.setId(projectDTO.getId());
        project.setName(projectDTO.getName());
        project.setDescription(projectDTO.getDescription());
        Person person = new Person();
            person.setId(projectDTO.getAuthorid());
        project.setUser(person);
        return project;
    }

    public ProjectApiDTO toDto(Project project){
        ProjectApiDTO projectDTO = new ProjectApiDTO();
        projectDTO.setId(project.getId());
        projectDTO.setName(project.getName());
        projectDTO.setDescription(project.getDescription());
        projectDTO.setAuthorId(project.getUser().getId());
        return projectDTO;
    }

    public Project toEntity(ProjectApiDTO projectDTO) {
        Project project = new Project();
        project.setId(projectDTO.getId());
        project.setName(projectDTO.getName());
        project.setDescription(projectDTO.getDescription());
        Person person = new Person();
            person.setId(projectDTO.getAuthorId());
        project.setUser(person);
        return project;
    }
}
