package it.intesys.academy.service;

import it.intesys.academy.dto.UserProjectDTO;
import it.intesys.academy.entity.User_Projects;
import it.intesys.academy.mapper.UserProjectMapper;
import it.intesys.academy.repository.UserProjectRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserProjectService {

    private final UserProjectRepository userProjectRepository;

    private final UserProjectMapper userProjectMapper;

    public UserProjectService(UserProjectRepository userProjectRepository, UserProjectMapper userProjectMapper) {

        this.userProjectRepository = userProjectRepository;
        this.userProjectMapper = userProjectMapper;
    }

    public boolean canThisUserReadThisProject(String username, int projectId) {


        return ! userProjectRepository.usernameProjectVisibility(authorId, projectId).isEmpty();

    }

    public void createUserProject(Integer projectId, String userName){
        User_Projects userProjects = new User_Projects();
        userProjects.setUsername(userName);
        userProjects.setProjectId(projectId);
        userProjectRepository.insertUserProject(userProjects);
    }

    public UserProjectDTO getUserProjectByProjectId(Integer userProjectId, String username){
        if(!canThisUserReadThisProject(username,userProjectId)){
            throw new RuntimeException("Error you cant read that");
        }
        User_Projects userProject = userProjectRepository.getUserProjectsById(userProjectId);
        return userProjectMapper.toDTO(userProject);
    }

    public List<UserProjectDTO> getuserProjects(String username){
        List<User_Projects> userProjects = userProjectRepository.getUserProjects(username);
        return userProjects.stream()
                .map(userProjectMapper::toDTO)
                .toList();
    }

    public UserProjectDTO createUserProjectByUserProject(UserProjectDTO userProjectDTO){
        User_Projects userProject = userProjectMapper.toEntity(userProjectDTO);
        //Controllo se projectId non è gia presente
        idJustUse(userProject);
        return userProjectMapper.toDTO(userProjectRepository.insertUserProject(userProject));
    }

    public UserProjectDTO updateUserProject(UserProjectDTO userProjectDTO){
        User_Projects userProjectUsername = userProjectMapper.toEntity(userProjectDTO);
        //Controllo se projectId non è gia presente
        idJustUse(userProjectUsername);
        return userProjectMapper.toDTO(userProjectRepository.updateUserProject(userProjectUsername));
    }

    public void deleteUserProject(Integer userProjectId, String username){
        User_Projects userProjectUsername = userProjectRepository.getUserProjectsById(userProjectId);
        if(!userProjectUsername.getUsername().equals(username)){
            throw new RuntimeException("Error you cant do that");
        }
        userProjectRepository.deleteUserProject(userProjectId);
    }

    /**
     * L'username che si è scelto è diverso da quello presente nel db
     * @param userProjects
     */
    public void sameUsername(User_Projects userProjects){
        User_Projects userProjectDatabase = userProjectRepository.getUserProjectsById(userProjects.getId());
        if(!userProjectDatabase.getUsername().equals(userProjects.getUsername())) {
            throw new RuntimeException("Error you cant do that");
        }
    }

    /**
     * L'idProject che si è scelto è gia stato utilizzato
     * @param userProject
     */
    public void idJustUse(User_Projects userProject){
        List<User_Projects> userProjects = userProjectRepository.getUserProjects(userProject.getUsername());
        List<Integer> projectIds = userProjects.stream().map(User_Projects::getProjectId).toList();
        if(projectIds.contains(userProject.getProjectId())){
            throw new RuntimeException("Error you cant do that");
        }
    }




}
