package t3h.vn.testonline.service;

import t3h.vn.testonline.dto.request.UserAnswerDto;
import t3h.vn.testonline.entities.UserAnswerEntity;

import java.util.List;


public interface UserAnswerService {


     void save(UserAnswerDto userAnswerDto, Long resultId, Long questionId, Long optionId);

     UserAnswerEntity getById(Long id);

     List<UserAnswerEntity> getAllByResultId(Long id);
}
