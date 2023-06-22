package it.intesys.academy.controller.rest;

import it.intesys.academy.dto.UserProjectDTO;
import it.intesys.academy.service.UserProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserProjectController {
    private final UserProjectService userProjectService;

    public UserProjectController(UserProjectService userProjectService){
        this.userProjectService = userProjectService;
    }

    @GetMapping("/userproject/{userprojectId}")
    public ResponseEntity<UserProjectDTO> getUserProject(@PathVariable Integer userprojectId, @RequestParam String username){
        return ResponseEntity.ok(userProjectService.getUserProjectByProjectId(userprojectId,username));
    }

    @GetMapping("/userproject")
    public ResponseEntity<List<UserProjectDTO>> getUserProjects(@RequestParam String username){
        return ResponseEntity.ok(userProjectService.getuserProjects(username));
    }

    @PostMapping("/userproject")
    public ResponseEntity<UserProjectDTO> postUserProject(@RequestBody UserProjectDTO userProjectDTO){
        return ResponseEntity.ok(userProjectService.createUserProjectByUserProject(userProjectDTO));
    }

    @PutMapping("/userproject")
    public ResponseEntity<UserProjectDTO> putUserProject(@RequestBody UserProjectDTO userProjectDTO){
        return ResponseEntity.ok(userProjectService.updateUserProject(userProjectDTO));
    }

    @DeleteMapping("/userproject/{userprojectId}")
    public ResponseEntity<Void> putUserProject(@PathVariable Integer userprojectId, @RequestParam String username){
        userProjectService.deleteUserProject(userprojectId,username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
