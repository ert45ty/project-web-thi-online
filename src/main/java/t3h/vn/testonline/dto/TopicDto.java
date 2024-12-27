package t3h.vn.testonline.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TopicDto {
    private Long id;
    private Long subject_id;

    @NotBlank(message = "Tên chủ đề không được phép để trống")
    @Size(min = 5, message = "Tên chủ đề phải lớn hơn 5 kí tự")
    private String name;

    @NotBlank(message = "Mô tả của chủ đề không được để trống")
    @Size(min = 10, message = "Mô tả của chủ đề phải lớn hơn 10 kí tự")
    private String description;
}
