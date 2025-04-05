package t3h.vn.testonline.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import t3h.vn.testonline.dto.request.OptionDto;
import t3h.vn.testonline.entities.OptionEntity;
import t3h.vn.testonline.repository.OptionRepo;
import t3h.vn.testonline.service.OptionService;

@Service
@RequiredArgsConstructor
public class OptionServiceImpl implements OptionService {

    private final OptionRepo optionRepo;
    private final QuestionServiceImpl questionService;

    public void save(OptionDto optionDto, Long id){
        OptionEntity optionEntity = new OptionEntity();
        BeanUtils.copyProperties(optionDto, optionEntity);
        optionEntity.setQuestion(questionService.getQuestionById(id));
        optionRepo.save(optionEntity);
    }

    public OptionEntity getById(Long id){
        return optionRepo.getById(id);
    }

}
