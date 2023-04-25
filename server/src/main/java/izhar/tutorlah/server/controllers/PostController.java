package izhar.tutorlah.server.controllers;


import izhar.tutorlah.server.models.Post;
import izhar.tutorlah.server.models.PostResponse;
import izhar.tutorlah.server.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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


}
