package it.intesys.academy.repository;

import it.intesys.academy.dto.ProjectDTO;
import it.intesys.academy.entity.Project;
import jakarta.persistence.EntityManager;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ProjectRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final EntityManager em;

    public ProjectRepository(NamedParameterJdbcTemplate jdbcTemplate, EntityManager em) {
        this.em = em;
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Project> readProjects(List<Integer> userProjectIds) {

        List<Project> projects = em.createQuery("from Project where id in (:projectIds)", Project.class)
                .setParameter("projectIds", userProjectIds)
                .getResultList();

        return projects;
    }

    public Project readProject(int projectId) {
        Project project = em.find(Project.class, projectId);
        return project;
    }

    public Project createProject(Project project) {
        em.persist(project);
        em.flush(); // serve per popolare il campo "id" di project
        return project;
    }
    public Project updateProject(Project project) {
        return em.merge(project);
    }

    public void deleteProject(Integer projectId) {
        jdbcTemplate.update("delete from Comment where comment.issueId in (select id from issue where PROJECTID = :projectId)",
                Map.of("projectId", projectId));
        jdbcTemplate.update("delete from Issue where PROJECTID = :projectId", Map.of("projectId", projectId));

        jdbcTemplate.update("delete from UserProject where projectid = :projectId", Map.of("projectId", projectId));

        em.createQuery("delete from Project p where id = :projectId")
                .setParameter("projectId", projectId)
                .executeUpdate();

    }

}
