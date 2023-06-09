package it.intesys.academy.service;

import it.intesys.academy.dto.UserProjectDTO;
import it.intesys.academy.repository.UserProjectRepository;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Service
public class SettingsService {

    private final UserProjectRepository userProjectRepository;

    public SettingsService(UserProjectRepository userProjectRepository) {
        this.userProjectRepository = userProjectRepository;
    }

    public List<Integer> getUserProjects(String username) {
        List<UserProjectDTO> users = userProjectRepository.searchUser(username);
        List<Integer> projects = new ArrayList<>();
        if(users!=null){
            for(UserProjectDTO project: users){
                projects.add(project.getProjectId());
            }
        }else{
            throw new RuntimeException("username failed");
        }
        return projects;
    }
}
