package it.intesys.academy.repository;

import it.intesys.academy.dto.ProjectDTO;
import it.intesys.academy.entity.Projects;
import jakarta.persistence.EntityManager;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ProjectRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final EntityManager em;

    public ProjectRepository(NamedParameterJdbcTemplate jdbcTemplate, EntityManager em) {

        this.jdbcTemplate = jdbcTemplate;
        this.em = em;
    }

    public List<Projects> readProjects(List<Integer> userProjectIds) {

        /**List<ProjectDTO> projects = jdbcTemplate.query("SELECT id, name, description FROM Projects where id in (:projectIds)",

                                                       Map.of("projectIds", userProjectIds),

                                                       BeanPropertyRowMapper.newInstance(ProjectDTO.class));

        return projects;*/
        return em.createQuery("from Projects Where id in (:ProjectIds)",Projects.class)
                .setParameter("ProjectIds", userProjectIds)
                .getResultList();
    }

    public Projects readProject(int projectId) {

        /**return jdbcTemplate.queryForObject("SELECT id, name, description FROM Projects where id = (:projectId)",

                                               Map.of("projectId", projectId),

                                               BeanPropertyRowMapper.newInstance(ProjectDTO.class));*/

        return em.find(Projects.class,projectId);

    }

    public Projects createProject(Projects project){
        /**Map<String, String> projects = Map.of("name",project.getName(),"description",project.getDescription());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("name", project.getName())
                .addValue("description", project.getDescription());
        jdbcTemplate.update("INSERT INTO PROJECTS (name,description) VALUES (:name,:description)",
                parameterSource, keyHolder);
        Integer id = keyHolder.getKey().intValue();
        project.setId(id);
        return project;**/
        em.createQuery("INSERT INTO Projects (name,description) VALUES (:name,:description)",Projects.class)
                .setParameter("name", project.getName())
                .setParameter("description", project.getDescription());
        return project;
    }

    public Projects updateProject(Projects project){
        /**jdbcTemplate.update("UPDATE PROJECTS SET name = :name, description = :description where id = :projectId",
                Map.of("name",projectDTO.getName(),
                        "description",projectDTO.getDescription(),
                        "projectId", projectDTO.getId()));*/
        return em.merge(project);
    }

    public void deleteProject(Integer projectId){
        em.remove(readProject(projectId));
    }

}
