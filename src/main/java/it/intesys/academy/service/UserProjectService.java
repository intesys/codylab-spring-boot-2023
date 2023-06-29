package it.intesys.academy.service;

import it.intesys.academy.controller.rest.errors.ProjectAccessException;
import it.intesys.academy.domain.Person;
import it.intesys.academy.domain.Project;
import it.intesys.academy.domain.UserProject;
import it.intesys.academy.repository.UserProjectRepository;
import it.intesys.academy.repository.UserProjectRepositoryApi;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProjectService {

    private final UserProjectRepository userProjectRepository;
    private final PersonService personService;
    private final UserProjectRepositoryApi userProjectRepositoryApi;

    public UserProjectService(UserProjectRepository userProjectRepository, PersonService personService, UserProjectRepositoryApi userProjectRepositoryApi) {

        this.userProjectRepository = userProjectRepository;
        this.personService = personService;
        this.userProjectRepositoryApi = userProjectRepositoryApi;
    }

    public boolean canThisUserReadThisProject(String username, int projectId) {

        return getUserProjects(username).contains(projectId);

    }
    public boolean alternativePermissionToProjects(String username, int projectId){
        return userProjectRepositoryApi.usernameProjectVisibility(username, projectId);
    }

    public List<Integer> getUserProjects(String username) {

        return userProjectRepository.findAllByPerson_Id(personService.getUserIdByUsername(username)).stream().map(UserProject::getProject).toList().stream().map(Project::getId).toList();
    }

    public void associateUserToProject(String username, Integer projectId) {
        Project project = new Project();
        project.setId(projectId);
        UserProject userProject = new UserProject();
        userProject.setProject(project);
        Person person = new Person();
        person.setId(personService.getUserIdByUsername(username));
        userProject.setPerson(person);

        userProjectRepository.save(userProject);
    }
    public void deleteUserProject(String username, Integer projectId){
        if(!getUserProjects(username).contains(projectId)){
            throw new ProjectAccessException("Project permission error", projectId);
        }

        for (UserProject us:userProjectRepository.findByProject_Id(projectId)) {
            userProjectRepository.deleteById(us.getId());
        }
    }

}
