package izhar.tutorlah.server.models;

import izhar.tutorlah.server.dto.PostDto;
import lombok.Data;

import java.util.List;

@Data
public class PostResponse {
    private List<PostDto> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
