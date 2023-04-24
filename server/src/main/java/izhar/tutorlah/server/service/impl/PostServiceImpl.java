package izhar.tutorlah.server.service.impl;

import izhar.tutorlah.server.dto.PostDto;
import izhar.tutorlah.server.models.PostResponse;
import izhar.tutorlah.server.repository.PostRepository;
import izhar.tutorlah.server.service.PostService;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    @Override
    public PostDto createPost(PostDto postDto) {
        return null;
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize) {
        return null;
    }

    @Override
    public PostDto getPostById(long id) {
        return null;
    }

    @Override
    public void deletePostBy(long id) {

    }

    @Override
    public PostResponse searchPosts(String searchTerm) {
        return null;
    }

    @Override
    public PostDto updatePost(long id, PostDto postDto) {
        return null;
    }
}
