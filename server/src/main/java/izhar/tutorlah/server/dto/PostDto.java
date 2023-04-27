package izhar.tutorlah.server.dto;

import lombok.Data;

import java.time.LocalDateTime;

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
}
