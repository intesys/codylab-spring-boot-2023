package it.intesys.academy.mapper;

import it.intesys.academy.controller.openapi.model.ProjectApiDTO;
import it.intesys.academy.controller.openapi.model.ProjectSettingsApiDTO;
import it.intesys.academy.domain.Project;
import it.intesys.academy.domain.ProjectSettings;
import it.intesys.academy.dto.ProjectDTO;
import org.springframework.stereotype.Component;
@Component
public class ProjectSettingsMapper {
    public ProjectSettingsApiDTO toApiDto(ProjectSettings projectSettings) {
        ProjectSettingsApiDTO projectSettingsApiDTO = new ProjectSettingsApiDTO();
        projectSettingsApiDTO.setProjectId(projectSettings.getProjectId());
        projectSettingsApiDTO.setShowReleasedIssues(projectSettings.getShowReleasedIssues());
        return projectSettingsApiDTO;
    }
    public ProjectSettings toEntity(ProjectSettingsApiDTO projectSettingsApiDTO) {
        ProjectSettings projectSettings = new ProjectSettings();
        projectSettings.setProjectId(projectSettingsApiDTO.getProjectId());
        projectSettings.setShowReleasedIssues(projectSettingsApiDTO.getShowReleasedIssues());
        return projectSettings;
    }
}
