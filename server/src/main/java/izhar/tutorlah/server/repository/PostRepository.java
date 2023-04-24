package izhar.tutorlah.server.repository;

import izhar.tutorlah.server.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
