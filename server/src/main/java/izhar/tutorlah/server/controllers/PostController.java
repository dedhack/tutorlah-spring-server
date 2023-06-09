package izhar.tutorlah.server.controllers;


import izhar.tutorlah.server.dto.PostDto;
import izhar.tutorlah.server.models.Post;
import izhar.tutorlah.server.models.PostResponse;
import izhar.tutorlah.server.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/post")
public class PostController {

    // wire in service logic
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // Get list of posts
    @GetMapping("")
    public ResponseEntity<PostResponse> getPost(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = true) int pageSize
    ){
        return new ResponseEntity<>(postService.getAllPosts(pageNo, pageSize), HttpStatus.OK);
    }

    // Get single post by id
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable long id){
        return new ResponseEntity<>(postService.getPostById(id), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<PostResponse> getAllPostsBySubject(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = true) int pageSize,
            @RequestParam(value ="subject" ) String subject
    ){
        return new ResponseEntity<>(postService.getAllPostsBySubject(pageNo, pageSize, subject), HttpStatus.OK);
    }

    // Create a post
    @PostMapping("/create/{userId}")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,
                                              @PathVariable(value = "userId") long userId){
        return new ResponseEntity<>(postService.createPost(userId,postDto), HttpStatus.CREATED);
    }

    // Update Post
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(
            @RequestBody PostDto postDto,
            @PathVariable(value = "id") long id
    ){
        PostDto response = postService.updatePost(id, postDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    // Delete Post
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable long id){
        postService.deletePostById(id);
        return new ResponseEntity<>("Post deleted successfully", HttpStatus.OK);
    }


}
