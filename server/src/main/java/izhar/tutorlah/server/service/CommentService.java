package izhar.tutorlah.server.service;

import izhar.tutorlah.server.dto.CommentDto;
import izhar.tutorlah.server.dto.PostDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment(long postId, long userId, PostDto postDto);

    List<CommentDto> getCommentsByPostId(long id);

    CommentDto getCommentById(long commentId, long postId);

    CommentDto updateComment(long postId, long userId, long commentId, PostDto postDto);

    void deleteComment(long postId, long commentId);

}
