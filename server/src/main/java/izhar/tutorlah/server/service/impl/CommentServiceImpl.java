package izhar.tutorlah.server.service.impl;

import izhar.tutorlah.server.dto.CommentDto;
import izhar.tutorlah.server.exceptions.CommentNotFoundException;
import izhar.tutorlah.server.exceptions.PostNotFoundException;
import izhar.tutorlah.server.models.Comment;
import izhar.tutorlah.server.models.Post;
import izhar.tutorlah.server.models.User;
import izhar.tutorlah.server.repository.CommentRepository;
import izhar.tutorlah.server.repository.PostRepository;
import izhar.tutorlah.server.repository.UserRepository;
import izhar.tutorlah.server.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private UserRepository userRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CommentDto createComment(long postId, long userId, CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto);

        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException("Post associated with review not found"));
        comment.setPost(post);

        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User associated with review not found"));
        comment.setUser(user);

        Comment newComment = commentRepository.save(comment);

        return commentMapToDto(newComment);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long id) {
        List<Comment> comments = commentRepository.findByPostId(id);

        return comments.stream().map(comment -> commentMapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(long commentId) {
//        Post post = postRepository.findById(postId).orElseThrow(()-> new PostNotFoundException("Post associated with review not found"));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException("Comment associated with Post not found"));

        // compare the post id retrieved from comment vs post
//        if (!Objects.equals(comment.getPost().getId(), post.getId())){
//            throw new CommentNotFoundException("This comment does not belong to a pokemon");
//        }

        return commentMapToDto(comment);
    }

    @Override
    public CommentDto updateComment(
            long postId, long commentId, CommentDto commentDto) {
        // find post
        Post post = postRepository.findById(postId).orElseThrow(()-> new PostNotFoundException("Post associated with comment not found"));

        // find comment
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new CommentNotFoundException("Comment associated with Post not found"));

        // find user
        User user = userRepository.findById(commentDto.getUserId()).orElseThrow(()-> new UsernameNotFoundException("User associated with Comment not found"));

        // TODO: check if userId matches before allowing update
        if(!Objects.equals(comment.getUser().getId(), user.getId())){
            throw new RuntimeException("Invalid");
        }

        // MAIN: set the values for comments
        comment.setContent(commentDto.getContent());
        comment.setCreationDateTime(LocalDateTime.now());

        // update comment repository
        Comment updatedComment = commentRepository.save(comment);

        // return mappedToDto comment
        return commentMapToDto(updatedComment);
    }

    @Override
    public void deleteComment( long commentId) {
        // check if correct post found
//        Post post = postRepository.findById(postId).orElseThrow(()->new PostNotFoundException("Post associated with Comment not found"));

        // check if correct comment found
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new CommentNotFoundException("Comment associated with Post not found"));

        // delete comment
        commentRepository.delete(comment);
    }

    // Mappers
    private CommentDto commentMapToDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setContent(comment.getContent());
        commentDto.setCreationDateTime(comment.getCreationDateTime());
        commentDto.setUserId(comment.getUser().getId());
        commentDto.setEmail(comment.getUser().getEmail());

        return commentDto;
    }

//    private UserDto userMapToDto(User user){
//        UserDto userDto = new UserDto();
//        userDto.setId(user.getId());
//        userDto.setFirstname(user.getFirstname());
//        userDto.setLastname(user.getLastname());
//        userDto.setEmail(user.getEmail());
//        return userDto;
//    }

    private Comment mapToEntity(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setContent(commentDto.getContent());
        comment.setCreationDateTime(LocalDateTime.now());
        return comment;
    }

}
