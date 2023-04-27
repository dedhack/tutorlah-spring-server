package izhar.tutorlah.server.dto;

import izhar.tutorlah.server.models.Comment;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private String subject;
    private LocalDateTime creationDateTime;
    private Long userId;
    private String email; // user.getUsername();
    private String firstname; // user.getFirstname();
    private List<CommentDto> comments;
}
