package t3h.vn.testonline.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResultDto {
    private Long id;
    private Long user_id;
    private Long exam_id;

    private Integer exam_duration;

    private Float score;

    private LocalDateTime created_at;

}
