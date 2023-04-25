package izhar.tutorlah.server.service;

import izhar.tutorlah.server.dto.CommentDto;
import izhar.tutorlah.server.dto.PostDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment(long postId, long userId, CommentDto commentDto);

    List<CommentDto> getCommentsByPostId(long id);

    CommentDto getCommentById(long commentId);

    CommentDto updateComment(long postId, long commentId, CommentDto commentDto);

    void deleteComment(long commentId);

}
