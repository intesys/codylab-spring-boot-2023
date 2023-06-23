package it.intesys.academy.service;

import it.intesys.academy.domain.Comment;
import it.intesys.academy.dto.CommentDTO;
import it.intesys.academy.mapper.CommentMapper;
import it.intesys.academy.mapper.IssueMapper;
import it.intesys.academy.repository.CommentRepository;
import it.intesys.academy.repository.IssueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private static final Logger log = LoggerFactory.getLogger(CommentService.class);


    private final CommentRepository commentRepository;

    private final UserProjectService userProjectService;

    private final CommentMapper commentMapper;
    private final IssueRepository issueRepository;

    public CommentService(IssueRepository issueRepository, CommentRepository commentRepository, UserProjectService userProjectService, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.userProjectService = userProjectService;
        this.commentMapper = commentMapper;
        this.issueRepository = issueRepository;
    }

    public List<CommentDTO> readCommentsByIssueId(Integer issueId) {

        return commentRepository.findByIssue_Id(issueId)
                .stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toList());

    }




    public CommentDTO createComment(CommentDTO commentDTO, String username) {

        log.info("Creating for user {}", username);


        Comment comment = commentRepository.save(commentMapper.toEntity(commentDTO));

        return commentMapper.toDto(comment);
    }

    public CommentDTO updateComment(CommentDTO commentDTO, String userName) {

        if (!userProjectService.canThisUserReadThisProject(userName, issueRepository.findIssueById(commentDTO.getIssueId()).getProject().getId())) {
            throw new RuntimeException("Security constraints violation");
        }

        Comment dbComment = commentRepository.findCommentById(commentDTO.getId());
        if ( dbComment.getIssue().getId() != commentDTO.getIssueId()) {
            throw new RuntimeException("Security constraints violation");
        }

        Comment updatedComment = commentRepository.save(commentMapper.toEntity(commentDTO));

        return commentMapper.toDto(updatedComment);
    }


    public void deleteComment(Integer commentId, String username) {

        Comment dbComment = commentRepository.findCommentById(commentId);

        if (!userProjectService.canThisUserReadThisProject(username, issueRepository.findIssueById(dbComment.getIssue().getId()).getProject().getId())) {
            throw new RuntimeException("Security constraints violation");
        }

        commentRepository.deleteById(dbComment.getId());
    }

}
