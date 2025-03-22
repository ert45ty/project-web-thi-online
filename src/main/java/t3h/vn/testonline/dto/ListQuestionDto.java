package t3h.vn.testonline.dto;

import jakarta.validation.Valid;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ListQuestionDto {
    @Valid
    private List<QuestionDto> questionDtoList = new ArrayList<>();

}
