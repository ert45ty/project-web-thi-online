package t3h.vn.testonline.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class QuestionDto {
    private Long id;
    private Long exam_id;

    @NotBlank(message = "Câu hỏi không được để trống")
    @Size(min = 10, message = "Câu hỏi quá ngắn")
    private String question_text;

    private List<OptionDto> options;

    private String image_name;

    MultipartFile fileImage;

    private  int correct;
}
