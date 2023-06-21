package it.intesys.academy.repository;

import it.intesys.academy.domain.Project;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends ListCrudRepository<Project, Integer>, ListPagingAndSortingRepository<Project, Integer> {

    /*
    private final EntityManager em;
    private final NamedParameterJdbcTemplate jdbcTemplate;
    public ProjectRepository(NamedParameterJdbcTemplate jdbcTemplate, EntityManager em) {
        this.em = em;
        this.jdbcTemplate = jdbcTemplate;
    }
    */

    public List<Project> findByIdIn(List<Integer> ids);

    /*
    public List<Project> readProjects(List<Integer> userProjectIds) {

        return em.createQuery("from Project p where id in (:projectIds)", Project.class)
                .setParameter("projectIds", userProjectIds)
                .getResultList();

    }
     */

    //public Project findById(int projectId);
    /*
    public Project readProject(int projectId) {
        return em.find(Project.class, projectId);
    }
     */

    /*
    public Project createProject(Project project) {
        em.persist(project);
        em.flush();
        return project;
    }

    public Project updateProject(Project project) {
        return em.merge(project);
    }
     */

    /*
    public void deleteProject(Integer projectId) {
        jdbcTemplate.update("delete from Comment where comment.issueId in (select id from issue where PROJECTID = :projectId)",
                Map.of("projectId", projectId));

        jdbcTemplate.update("delete from Issue where PROJECTID = :projectId", Map.of("projectId", projectId));


        jdbcTemplate.update("delete from UserProject where projectid = :projectId", Map.of("projectId", projectId));

        em.createQuery("delete from Project p where id = :projectId")
                .setParameter("projectId", projectId)
                .executeUpdate();

    }
     */

}
