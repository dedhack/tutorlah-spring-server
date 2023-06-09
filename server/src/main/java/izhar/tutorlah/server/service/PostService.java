package izhar.tutorlah.server.service;

import izhar.tutorlah.server.dto.PostDto;
import izhar.tutorlah.server.models.Post;
import izhar.tutorlah.server.models.PostResponse;

public interface PostService {

    PostDto createPost(long userId, PostDto postDto);

    PostResponse getAllPosts(int pageNo, int pageSize);

    PostResponse getAllPostsBySubject(int pageNo, int pageSize, String subject);

    PostDto getPostById(long id);
    void deletePostById(long id);
    PostResponse searchPosts(String searchTerm);
    PostDto updatePost(long id, PostDto postDto);

}
