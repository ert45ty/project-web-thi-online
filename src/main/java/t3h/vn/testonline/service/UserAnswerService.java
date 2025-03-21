package t3h.vn.testonline.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import t3h.vn.testonline.dto.UserAnswerDto;
import t3h.vn.testonline.entities.ResultEntity;
import t3h.vn.testonline.entities.UserAnswerEntity;
import t3h.vn.testonline.repository.UserAnswerRepo;

import java.util.List;

@Service
public class UserAnswerService {

    @Autowired
    UserAnswerRepo userAnswerRepo;
    @Autowired
    OptionService optionService;
    @Autowired
    QuestionService questionService;
    @Autowired
    ResultService resultService;


    public void save(UserAnswerDto userAnswerDto, Long resultId, Long questionId, Long optionId){
        UserAnswerEntity userAnswerEntity = new UserAnswerEntity();
        BeanUtils.copyProperties(userAnswerDto, userAnswerEntity);
        userAnswerEntity.setResult(resultService.getById(resultId));
        userAnswerEntity.setQuestion(questionService.getQuestionById(questionId));
        if (optionId == null) userAnswerEntity.setOption(null);
        else userAnswerEntity.setOption(optionService.getById(optionId));
        userAnswerRepo.save(userAnswerEntity);
    }

    public UserAnswerEntity getById(Long id){
        return userAnswerRepo.getById(id);
    }

    public List<UserAnswerEntity> getAllByResultId(Long id){
        return userAnswerRepo.getAllByResult_Id(id);
    }
}
