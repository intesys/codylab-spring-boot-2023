package it.intesys.academy.repository;

import it.intesys.academy.dto.ProjectDTO;
import it.intesys.academy.entity.Project;
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

    public List<Project> readProjects(List<Integer> userProjectIds) {

        /**List<ProjectDTO> projects = jdbcTemplate.query("SELECT id, name, description FROM Projects where id in (:projectIds)",

                                                       Map.of("projectIds", userProjectIds),

                                                       BeanPropertyRowMapper.newInstance(ProjectDTO.class));

        return projects;*/
        return em.createQuery("from Projects Where id in (:ProjectIds)",Project.class)
                .setParameter("ProjectIds", userProjectIds)
                .getResultList();
    }

    public Project readProject(int projectId) {

        /**return jdbcTemplate.queryForObject("SELECT id, name, description FROM Projects where id = (:projectId)",

                                               Map.of("projectId", projectId),

                                               BeanPropertyRowMapper.newInstance(ProjectDTO.class));*/

        return em.find(Project.class,projectId);

    }

    public Project createProject(Project project){
        /**Map<String, String> project = Map.of("name",projectDTO.getName(),"description",projectDTO.getDescription());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("name", projectDTO.getName())
                .addValue("description", projectDTO.getDescription());
        jdbcTemplate.update("INSERT INTO PROJECTS (name,description) VALUES (:name,:description)",
                parameterSource, keyHolder);
        return keyHolder.getKey().intValue();*/
        em.persist(project);
        em.flush();
        return project;
    }

    public Project updateProject(Project project){
        /**jdbcTemplate.update("UPDATE PROJECTS SET name = :name, description = :description where id = :projectId",
                Map.of("name",projectDTO.getName(),
                        "description",projectDTO.getDescription(),
                        "projectId", projectDTO.getId()));*/
        return em.merge(project);
    }

    public void deleteProject(Integer projectId){
        /**jdbcTemplate.update("Delete FROM Projects where id = :projectId",
                Map.of("projectId",projectId));*/
        em.createQuery("DELETE from Projects where id = :projectid", Project.class)
                .setParameter("projectid",projectId)
                .getResultList();
    }

}
