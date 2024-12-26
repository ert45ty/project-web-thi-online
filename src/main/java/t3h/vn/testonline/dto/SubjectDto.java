package t3h.vn.testonline.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SubjectDto {
    private Long id;

    @NotBlank(message = "Tên môn thi không được để trống")
    @Size(min = 2, message = "Tên môn thi phải lớn hơn 2 kí tự")
    private String name;
}
