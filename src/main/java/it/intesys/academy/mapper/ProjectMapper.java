package it.intesys.academy.mapper;

import it.intesys.academy.dto.ProjectDTO;
import it.intesys.academy.entity.Projects;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {
    public ProjectDTO toDTO(Projects project){
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(project.getId());
        projectDTO.setName(project.getName());
        projectDTO.setDescription(project.getDescription());
        return projectDTO;
    }

    public Projects toEntity(ProjectDTO projectDTO){
        Projects project = new Projects();
        project.setId(projectDTO.getId());
        project.setName(projectDTO.getName());
        project.setDescription(projectDTO.getDescription());
        return project;
    }

}
