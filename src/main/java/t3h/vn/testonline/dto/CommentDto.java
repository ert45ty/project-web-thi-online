package t3h.vn.testonline.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {
    private Long id;
    private Long topic_id;
    private Long user_id;

    @NotBlank(message = "Bình luận phải chứa ít nhất 1 kí tự")
    private String content;

    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
