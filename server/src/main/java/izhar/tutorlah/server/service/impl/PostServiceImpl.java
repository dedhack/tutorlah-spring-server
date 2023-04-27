package izhar.tutorlah.server.service.impl;

import izhar.tutorlah.server.dto.CommentDto;
import izhar.tutorlah.server.dto.PostDto;
import izhar.tutorlah.server.exceptions.PostNotFoundException;
import izhar.tutorlah.server.models.Comment;
import izhar.tutorlah.server.models.Post;
import izhar.tutorlah.server.models.PostResponse;
import izhar.tutorlah.server.models.User;
import izhar.tutorlah.server.repository.CommentRepository;
import izhar.tutorlah.server.repository.PostRepository;
import izhar.tutorlah.server.repository.UserRepository;
import izhar.tutorlah.server.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    private UserRepository userRepository;

    private CommentRepository commentRepository;


    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    public PostDto createPost(long userId, PostDto postDto) {

        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setSubject(postDto.getSubject());
        post.setCreationDateTime(LocalDateTime.now());

        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User associated with review not found"));
        post.setUser(user);

        Post newPost = postRepository.save(post);

        PostDto postDtoResponse = new PostDto();
        postDtoResponse.setId(newPost.getId());
        postDtoResponse.setTitle(newPost.getTitle());
        postDtoResponse.setContent(newPost.getContent());
        postDtoResponse.setUserId(newPost.getUser().getId());
        postDtoResponse.setSubject(newPost.getSubject());
        postDtoResponse.setEmail(newPost.getUser().getUsername());
        postDtoResponse.setFirstname(newPost.getUser().getFirstname());
        postDtoResponse.setCreationDateTime(newPost.getCreationDateTime());

        return postDtoResponse;
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Post> posts = postRepository.findAll(pageable); // retrieving post objects

        List<Post> listOfPosts = posts.getContent();
        List<PostDto> content = listOfPosts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(postResponse.isLast());

        return postResponse;
    }

    @Override
    public PostResponse getAllPostsBySubject(int pageNo, int pageSize, String subject) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("creationDateTime").descending());
        Page<Post> posts = postRepository.findAllBySubject(subject, pageable); // retrieving post objects by subject

        List<Post> listOfPosts = posts.getContent();
        List<PostDto> content = listOfPosts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(postResponse.isLast());

        return postResponse;
    }


    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new PostNotFoundException("Post could not be found"));

        // get comments related to post
        List<Comment> comments = commentRepository.findByPostId(id);
        List<CommentDto> commentDtos = comments.stream().map(comment -> commentMapToDto(comment)).collect(Collectors.toList());
        PostDto postDto = mapToDto(post);
        postDto.setComments(commentDtos);

        return postDto;
    }

    @Override
    public void deletePostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new PostNotFoundException("Post could not be found"));
        postRepository.delete(post);
    }

    @Override
    public PostResponse searchPosts(String searchTerm) {
        return null;
    }

    @Override
    public PostDto updatePost(long id, PostDto postDto) {
        Post post = postRepository.findById(id).orElseThrow(()-> new PostNotFoundException("Post could not be updated"));

        post.setTitle(postDto.getContent());
        post.setContent(postDto.getContent());
        post.setCreationDateTime(LocalDateTime.now());

        Post updatedPost = postRepository.save(post);
        return mapToDto(updatedPost);

    }

    // Mapper Functions
    private PostDto mapToDto(Post post){
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setSubject(post.getSubject());
        postDto.setCreationDateTime(post.getCreationDateTime());
        postDto.setUserId(post.getUser().getId());
        postDto.setEmail(post.getUser().getUsername());
        postDto.setFirstname(post.getUser().getFirstname());
        return postDto;
    }

    private Post mapToEntity(PostDto postDto){
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setCreationDateTime(postDto.getCreationDateTime());
        return post;
    }

    private CommentDto commentMapToDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setContent(comment.getContent());
        commentDto.setCreationDateTime(comment.getCreationDateTime());
        commentDto.setUserId(comment.getUser().getId());
        commentDto.setEmail(comment.getUser().getEmail());
        commentDto.setFirstname(comment.getUser().getFirstname());

        return commentDto;
    }
}
