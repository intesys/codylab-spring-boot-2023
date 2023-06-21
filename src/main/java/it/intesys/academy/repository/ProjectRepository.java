package it.intesys.academy.repository;

import it.intesys.academy.dto.ProjectDTO;
import it.intesys.academy.entity.Project;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ProjectRepository {

private final EntityManager em;
    public ProjectRepository(NamedParameterJdbcTemplate jdbcTemplate, EntityManager em) {
        this.em = em;

    }

    public List<Project> readProjects(List<Integer> userProjectIds) {

        return em.createQuery("from Project p where id in (:projectIds)", Project.class)
                .setParameter("projectIds", userProjectIds)
                .getResultList();
    }

    public Project readProject(int projectId) {

        return em.createQuery("from Project p where id = :projectId", Project.class)
                .setParameter("projectId", projectId)
                .getSingleResult();


    }

    public Project createProject(Project project) {
        em.persist(project);
        em.flush();
        return project;
    }

    public Project updateProject(Project project) {
        return em.merge(project);
    }
    @Transactional
    public void deleteProject(Integer projectId) {
        em.createNativeQuery("DELETE FROM Comment c WHERE issueId IN (SELECT i.id FROM Issue i WHERE i.projectId = :projectId)", Project.class)
                .setParameter("projectId", projectId)
                .executeUpdate();

        em.createNativeQuery("delete from Issue where projectId = :projectId", Project.class)
                .setParameter("projectId", projectId)
                .executeUpdate();

        em.createNativeQuery("delete from UserProject where projectId = :projectId", Project.class)
                .setParameter("projectId", projectId)
                .executeUpdate();

        em.createNativeQuery("delete from Project where id = :projectId", Project.class)
                .setParameter("projectId", projectId)
                .executeUpdate();

    }

}
