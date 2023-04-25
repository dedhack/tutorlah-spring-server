package izhar.tutorlah.server.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {
    private long id;
    private String content;
    private LocalDateTime creationDateTime;

}
