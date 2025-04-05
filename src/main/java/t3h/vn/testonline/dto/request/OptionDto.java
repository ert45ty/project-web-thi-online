package t3h.vn.testonline.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OptionDto {
    private Long id;
    private Long question_id;

    @NotBlank(message = "Đáp án phải chứa ít nhất 1 kí tự")
    private String option_text;

    private Boolean is_correct;

}
