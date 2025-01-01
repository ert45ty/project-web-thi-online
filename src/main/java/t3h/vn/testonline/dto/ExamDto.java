package t3h.vn.testonline.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ExamDto {

    private Long id;

    private Long topic_id;

    @NotBlank(message = "Tiêu đề không được để rỗng")
    @Size(min = 5)
    private String title;

    private Integer duration;

    private Integer total_question;
}
