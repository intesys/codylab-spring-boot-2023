package it.intesys.academy.service;

import it.intesys.academy.domain.Project;
import it.intesys.academy.domain.UserProject;
import it.intesys.academy.repository.PersonRepository;
import it.intesys.academy.repository.ProjectRepository;
import it.intesys.academy.repository.UserProjectRepository;
import it.intesys.academy.repository.UserProjectsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProjectService {

    private final UserProjectRepository userProjectRepository;

    private final UserProjectsRepository userProjectsRepository;

    private final ProjectRepository projectRepository;

    private final PersonRepository personRepository;

    public UserProjectService(UserProjectRepository userProjectRepository,
                              PersonRepository personRepository,
                              ProjectRepository projectRepository,
                              UserProjectsRepository userProjectsRepository) {

        this.userProjectRepository = userProjectRepository;
        this.personRepository = personRepository;
        this.projectRepository = projectRepository;
        this.userProjectsRepository = userProjectsRepository;

    }

    public boolean canThisUserReadThisProject(String username, int projectId) {
        if(username.equals("ecostanzi") || username.equals("zato")){
        return ! userProjectsRepository.usernameProjectVisibility(username,projectId).isEmpty();}
        else{
            return ! userProjectRepository.findUserProjectsByPersonUsernameAndProjectId(username,projectId).isEmpty();
        }

    }

    public List<Integer> getUserProjects(String username) {

        List<UserProject> projects =  userProjectRepository.findUserProjectByPersonUsername(username);
        return projects.stream()
                .map(UserProject::getProject)
                .map(Project::getId)
                .toList();

    }

    public void associateUserToProject(String username, Integer projectId) {
        UserProject userProject = new UserProject();

        userProject.setProject(projectRepository.findById(projectId).get());

        userProject.setPerson(personRepository.findPersonByUsername(username));

        userProjectRepository.save(userProject);
    }

}
