package izhar.tutorlah.server.service;

import izhar.tutorlah.server.dto.PostDto;
import izhar.tutorlah.server.models.Post;
import izhar.tutorlah.server.models.PostResponse;

public interface PostService {

    PostDto createPost(PostDto postDto);

    PostResponse getAllPosts(int pageNo, int pageSize);

    PostDto getPostById(long id);
    void deletePostById(long id);
    PostResponse searchPosts(String searchTerm);
    PostDto updatePost(long id, PostDto postDto);

}
