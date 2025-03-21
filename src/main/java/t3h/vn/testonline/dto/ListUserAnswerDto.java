package t3h.vn.testonline.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ListUserAnswerDto {
    private List<UserAnswerDto> answerDtoList = new ArrayList<>();
}
