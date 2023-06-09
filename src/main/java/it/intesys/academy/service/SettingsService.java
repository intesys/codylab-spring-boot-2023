package it.intesys.academy.service;

import it.intesys.academy.dto.UserProjectDTO;
import it.intesys.academy.repository.UserProjectRepository;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Service
public class SettingsService {

    private final DataSource dataSource;

    private final UserProjectRepository userProjectRepository;

    public SettingsService(DataSource dataSource, UserProjectRepository userProjectRepository) {
        this.dataSource = dataSource;
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
