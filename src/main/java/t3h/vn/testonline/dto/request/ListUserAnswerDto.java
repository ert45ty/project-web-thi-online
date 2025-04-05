package t3h.vn.testonline.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ListUserAnswerDto {
    private List<UserAnswerDto> answerDtoList = new ArrayList<>();
}
