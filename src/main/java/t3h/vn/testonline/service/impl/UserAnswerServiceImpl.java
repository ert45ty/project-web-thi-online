package t3h.vn.testonline.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import t3h.vn.testonline.dto.request.UserAnswerDto;
import t3h.vn.testonline.entities.UserAnswerEntity;
import t3h.vn.testonline.repository.UserAnswerRepo;
import t3h.vn.testonline.service.UserAnswerService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAnswerServiceImpl implements UserAnswerService {

    private final UserAnswerRepo userAnswerRepo;
    private final OptionServiceImpl optionService;
    private final QuestionServiceImpl questionService;
    private final ResultServiceImpl resultService;

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
