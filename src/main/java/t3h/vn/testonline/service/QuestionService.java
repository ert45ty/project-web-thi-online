package t3h.vn.testonline.service;

import t3h.vn.testonline.dto.request.QuestionDto;
import t3h.vn.testonline.entities.QuestionEntity;

import java.util.List;

public interface QuestionService {

    List<QuestionEntity> getAllByExamId(Long id);

    QuestionEntity save(QuestionDto questionDto, Long id);

    QuestionEntity getQuestionById(Long id);

    void deleteByExamId(Long id);


}
