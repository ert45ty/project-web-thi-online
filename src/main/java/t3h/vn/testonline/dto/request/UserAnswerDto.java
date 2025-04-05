package t3h.vn.testonline.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import t3h.vn.testonline.entities.UserAnswerEntity;

import java.util.List;

@Getter
@Setter
public class UserAnswerDto {
    private Long result_id;

    private Long question_id;

    private Long option_id;

}
