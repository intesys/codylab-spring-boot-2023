package it.intesys.academy.repository;

import it.intesys.academy.dto.UserProjectDTO;
import it.intesys.academy.entity.User_Projects;
import it.intesys.academy.service.IssueService;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.List;
import java.util.Optional;

@Repository
public class UserProjectRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final EntityManager em;

    private final RestTemplate restTemplate;
    private static final Logger log = LoggerFactory.getLogger(IssueService.class);

    public UserProjectRepository(NamedParameterJdbcTemplate jdbcTemplate, EntityManager em,RestTemplate restTemplate) {

        this.jdbcTemplate = jdbcTemplate;
        this.em = em;
        this.restTemplate = restTemplate;
    }

    public Optional<User_Projects> usernameProjectVisibility(String username, Integer projectId) {
        try{
            ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:3003/api/v1/projects/"+username,String.class);
            log.info("code"+response.getStatusCode());
            log.info(response.getBody());
            return Optional.empty();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        /**
        User_Projects userProjects = em.createQuery("FROM User_Projects where projectId = (:projectId) and username = (:username)",User_Projects.class)
                .setParameter("projectId", projectId)
                .setParameter("username", username)
                .getSingleResult();

        return Optional.ofNullable(userProjects);*/
    }

    public List<User_Projects> getUserProjects(String username) {

        return em.createQuery("from User_Projects where username=:username",User_Projects.class)
                .setParameter("username",username)
                .getResultList();
    }

    public User_Projects getUserProjectsById(Integer userProjectId){
        return em.find(User_Projects.class,userProjectId);
    }

    public User_Projects insertUserProject(User_Projects userProjects){
        em.persist(userProjects);
        em.flush();
        return userProjects;
    }

    public User_Projects updateUserProject(User_Projects userProjects){
        return em.merge(userProjects);
    }

    public void deleteUserProject(Integer userProjectId){
        em.remove(getUserProjectsById(userProjectId));
    }

}
