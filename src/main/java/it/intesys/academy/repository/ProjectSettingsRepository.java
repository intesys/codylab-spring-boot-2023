package it.intesys.academy.repository;

import it.intesys.academy.domain.Project;
import it.intesys.academy.domain.ProjectSettings;
import it.intesys.academy.domain.UserProject;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectSettingsRepository extends ListCrudRepository<ProjectSettings, Integer>, ListPagingAndSortingRepository<ProjectSettings, Integer> {
    ProjectSettings findByProjectId(Integer id);

}
