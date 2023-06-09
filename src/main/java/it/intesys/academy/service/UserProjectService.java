package it.intesys.academy.service;

import it.intesys.academy.repository.UserProjectRepository;
import org.springframework.stereotype.Service;

@Service
public class UserProjectService {

    private final UserProjectRepository userProjectRepository;

    public UserProjectService(UserProjectRepository userProjectRepository) {

        this.userProjectRepository = userProjectRepository;
    }

    public boolean canThisUserReadThisProject(String username, int projectId) {

        return ! userProjectRepository.usernameProjectVisibility(username, projectId).isEmpty();

    }

}
