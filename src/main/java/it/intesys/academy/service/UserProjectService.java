package it.intesys.academy.service;

import it.intesys.academy.domain.Person;
import it.intesys.academy.domain.Project;
import it.intesys.academy.domain.UserProject;
import it.intesys.academy.repository.UserProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProjectService {

    private final UserProjectRepository userProjectRepository;
    private final PersonService personService;

    public UserProjectService(UserProjectRepository userProjectRepository, PersonService personService) {

        this.userProjectRepository = userProjectRepository;
        this.personService = personService;
    }

    public boolean canThisUserReadThisProject(String username, int projectId) {

        return getUserProjects(username).contains(projectId);

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

}
