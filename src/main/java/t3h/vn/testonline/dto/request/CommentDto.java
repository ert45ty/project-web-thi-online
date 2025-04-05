package t3h.vn.testonline.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentDto {
    private Long id;
    private Long topic_id;
    private Long user_id;

    @NotBlank(message = "Bình luận phải chứa ít nhất 1 kí tự")
    private String content;

    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
