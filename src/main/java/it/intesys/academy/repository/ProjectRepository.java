package it.intesys.academy.repository;

import it.intesys.academy.dto.ProjectDTO;
import it.intesys.academy.service.ProjectService;
import it.intesys.academy.service.SettingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ProjectRepository {

    private static final Logger log = LoggerFactory.getLogger(ProjectService.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;


    public ProjectRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Restituisce tutti i Projects
     * @return
     */
    public List<ProjectDTO> readProjects(){
        List<ProjectDTO> projects = jdbcTemplate.query("SELECT id, name, description ",
                BeanPropertyRowMapper.newInstance(ProjectDTO.class));
        return projects;
    }

    /**
     * Restituisce una lista di Projects in base alla lista di id passata
     * @param projectIds
     * @return
     */
    public List<ProjectDTO> searchProjects(List<Integer> projectIds){
        List<ProjectDTO> projects = jdbcTemplate.query("SELECT id, name, description FROM Projects where id in (:projectIds)",

                Map.of("projectIds", projectIds),

                BeanPropertyRowMapper.newInstance(ProjectDTO.class));
        return projects;
    }

    public List<ProjectDTO> searchProject(Integer projectIds){
        return jdbcTemplate.query("SELECT id, name, description FROM Projects where id = :projectIds",

                Map.of("projectIds", projectIds),

                BeanPropertyRowMapper.newInstance(ProjectDTO.class));
    }
}
