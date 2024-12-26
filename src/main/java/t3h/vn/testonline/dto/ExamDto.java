package t3h.vn.testonline.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ExamDto {

    private Long id;

    private Long topic_id;

    @NotBlank(message = "Tiêu đề không được để rỗng")
    @Size(min = 10)
    private String title;

    @NotBlank(message = "Mô tả không được phép rỗng")
    @Size(min = 30, message = "Mô tả ít nhất 30 ký tự")
    private String description;

    private Integer duration;

    private Integer total_question;
}
