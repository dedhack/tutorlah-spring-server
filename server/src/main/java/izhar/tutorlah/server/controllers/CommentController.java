package izhar.tutorlah.server.controllers;

import izhar.tutorlah.server.dto.CommentDto;
import izhar.tutorlah.server.models.Comment;
import izhar.tutorlah.server.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CommentController {

    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/post/{postId}/comment/{userId}")
    public ResponseEntity<CommentDto> createComment(
            @RequestBody CommentDto commentDto,
            @PathVariable(value = "postId") long postId,
            @PathVariable(value = "userId") long userId
    ){

        return new ResponseEntity<>(commentService.createComment(postId,userId,commentDto), HttpStatus.CREATED);
    }

    @GetMapping("/post/{postId}/comments")
    public List<CommentDto> getCommentsByPostId(
            @PathVariable(name = "postId") long postId
    ){
        return commentService.getCommentsByPostId(postId);
    }

    @GetMapping("/comment/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(
//            @PathVariable(name = "postId") long postId,
            @PathVariable(name = "commentId") long commentId
    ){
        CommentDto commentDto = commentService.getCommentById(commentId);
        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }
}
