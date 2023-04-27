package izhar.tutorlah.server.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {
    private Long id;
    private String content;
    private LocalDateTime creationDateTime;

    private Long userId;
    private String email;
    private String firstname;

}
