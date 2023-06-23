package it.intesys.academy.service;

import it.intesys.academy.domain.Project;
import it.intesys.academy.domain.UserProject;
import it.intesys.academy.repository.PersonRepository;
import it.intesys.academy.repository.ProjectRepository;
import it.intesys.academy.repository.UserProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProjectService {

    private final PersonRepository personRepository;

    private final ProjectRepository projectRepository;

    private final UserProjectRepository userProjectRepository;

    public UserProjectService(PersonRepository personRepository, ProjectRepository projectRepository, UserProjectRepository userProjectRepository) {
        this.personRepository = personRepository;
        this.projectRepository = projectRepository;
        this.userProjectRepository = userProjectRepository;
    }

    public boolean canThisUserReadThisProject(String username, int projectId) {

        return ! userProjectRepository.findUserProjectsByProjectIdAndPersonUsername(projectId, username).isEmpty();

    }

    public List<Integer> getUserProjects(String username) {
        List<UserProject> projects = userProjectRepository.findUserProjectsByPersonUsername(username);
        return projects
                .stream()
                .map(UserProject::getProject)
                .map(Project::getId)
                .toList();
    }

    public void associateUserToProject(String username, Integer projectId) {
    UserProject userProject= new UserProject();
    userProject.setProject(projectRepository.findById(projectId).get());
    userProject.setPerson(personRepository.findPersonByUsername(username));
    userProjectRepository.save(userProject);

    }

}
