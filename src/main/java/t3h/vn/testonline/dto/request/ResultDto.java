package t3h.vn.testonline.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ResultDto {
    private Long id;
    private Long user_id;
    private Long exam_id;

    private Integer exam_duration;

    private Float score;

    private LocalDateTime created_at;

}
