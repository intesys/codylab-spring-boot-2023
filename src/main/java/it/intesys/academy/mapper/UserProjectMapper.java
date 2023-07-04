package it.intesys.academy.mapper;

import it.intesys.academy.dto.UserProjectDTO;
import it.intesys.academy.entity.User_Projects;
import org.springframework.stereotype.Component;

@Component
public class UserProjectMapper {

    public User_Projects toEntity(UserProjectDTO userProjectDTO){
        User_Projects userProjects = new User_Projects();
        userProjects.setId(userProjectDTO.getId());
        userProjects.setUsername(userProjectDTO.getUsername());
        userProjects.setProjectId(userProjectDTO.getProjectId());
        return userProjects;
    }

    public UserProjectDTO toDTO(User_Projects userProjects){
        UserProjectDTO userProjectDTO = new UserProjectDTO();
        userProjectDTO.setId(userProjects.getId());
        userProjectDTO.setUsername(userProjects);
        userProjectDTO.setProjectId(userProjects.getProjectId());
        return userProjectDTO;
    }
}
