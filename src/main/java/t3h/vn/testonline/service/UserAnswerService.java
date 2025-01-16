package t3h.vn.testonline.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import t3h.vn.testonline.dto.UserAnswerDto;
import t3h.vn.testonline.entities.ResultEntity;
import t3h.vn.testonline.entities.UserAnswerEntity;
import t3h.vn.testonline.repository.UserAnswerRepo;

@Service
public class UserAnswerService {

    @Autowired
    UserAnswerRepo userAnswerRepo;
    @Autowired
    OptionService optionService;
    @Autowired
    QuestionService questionService;

    public void save(UserAnswerDto userAnswerDto, ResultEntity resultEntity){
        UserAnswerEntity userAnswerEntity = new UserAnswerEntity();
        if (userAnswerDto.getOption_id() != null){
        userAnswerEntity.setOption(optionService.getById(userAnswerDto.getOption_id()));
        }
        userAnswerEntity.setQuestion(questionService.getQuestionById(userAnswerDto.getQuestion_id()));
        userAnswerEntity.setResult(resultEntity);
        userAnswerRepo.save(userAnswerEntity);
    }
}
