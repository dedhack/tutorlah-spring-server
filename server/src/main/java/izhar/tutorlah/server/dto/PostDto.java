package izhar.tutorlah.server.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime creationDateTime;
    private Long userId;

}
