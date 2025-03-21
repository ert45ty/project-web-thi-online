package t3h.vn.testonline.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class OptionDto {
    private Long id;
    private Long question_id;

    @NotBlank(message = "Đáp án phải chứa ít nhất 1 kí tự")
    private String option_text;

    private Boolean is_correct;

}
