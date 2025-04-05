package t3h.vn.testonline.dto.request;

import jakarta.validation.Valid;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ListQuestionDto {
    @Valid
    private List<QuestionDto> questionDtoList = new ArrayList<>();

}
